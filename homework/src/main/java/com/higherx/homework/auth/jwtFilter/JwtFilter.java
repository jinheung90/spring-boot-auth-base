package com.higherx.homework.auth.jwtFilter;

import com.higherx.homework.auth.TokenProvider;
import com.higherx.homework.auth.dao.ParsedUserDataByJwtToken;
import com.higherx.homework.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);

        if(StringUtils.hasText(jwt)) {
            ParsedUserDataByJwtToken parsedUserDataByJwtToken = tokenProvider.getUserIdAndAuthorityByJwtAccessToken(jwt);
            if(parsedUserDataByJwtToken != null) {
                setAuthentication(parsedUserDataByJwtToken, jwt);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    private void setAuthentication(ParsedUserDataByJwtToken userIdAndAuthorities, String jwt) {

        System.out.println(userIdAndAuthorities.getUserId());
        org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(userIdAndAuthorities.getUserId().toString(), "", userIdAndAuthorities.getAuthorities());
        Authentication authentication =  new UsernamePasswordAuthenticationToken(principal, jwt, userIdAndAuthorities.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
