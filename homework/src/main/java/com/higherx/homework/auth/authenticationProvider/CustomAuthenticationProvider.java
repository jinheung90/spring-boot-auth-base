package com.higherx.homework.auth.authenticationProvider;


import com.higherx.homework.domain.user.entity.User;
import com.higherx.homework.domain.user.repository.UserRepository;
import com.higherx.homework.domain.user.service.UserService;
import com.higherx.homework.errorHandling.customRuntimeException.RuntimeExceptionWithCode;
import com.higherx.homework.errorHandling.errorEnums.GlobalErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;
import com.higherx.homework.auth.userDetail.CustomUserDetail;
@RequiredArgsConstructor
@Component
@Slf4j
public class CustomAuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Authentication authenticate(String userId, String password) {

        User user = userRepository.findByUserLoginId(userId)
                .orElseThrow(() -> new RuntimeExceptionWithCode(GlobalErrorCode.NOT_EXISTS_USER));
        if(!passwordEncoder.matches(password ,user.getPassword())) {
            throw new RuntimeExceptionWithCode(GlobalErrorCode.NOT_EXISTS_USER, "패스워드 불일치");
        }

        return new UsernamePasswordAuthenticationToken(
            new CustomUserDetail(
                user.getAuthorities().stream().map(a -> new SimpleGrantedAuthority(a.getAuthority())).collect(Collectors.toList()),
                user.getUserLoginId(),
                "",
                user.getId()
            ),
            null, user.getAuthorities());
    }
}
