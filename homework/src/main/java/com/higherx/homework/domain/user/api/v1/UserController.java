package com.higherx.homework.domain.user.api.v1;

import com.higherx.homework.auth.TokenProvider;
import com.higherx.homework.auth.authenticationProvider.CustomAuthenticationProvider;
import com.higherx.homework.auth.dao.ParsedUserDataByJwtToken;
import com.higherx.homework.auth.userDetail.CustomUserDetail;
import com.higherx.homework.commons.CommonFunctions;
import com.higherx.homework.commons.ResponseFormat;
import com.higherx.homework.domain.user.entity.User;
import com.higherx.homework.domain.user.dto.LoginRequest;
import com.higherx.homework.domain.user.dto.SignupRequest;
import com.higherx.homework.domain.user.dto.UserDto;
import com.higherx.homework.domain.user.dto.UserResponse;
import com.higherx.homework.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private static final String refresh_token = "REFRESH_TOKEN";
    private final UserService userService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final TokenProvider tokenProvider;
    @PostMapping("/v1/auth/login")
    public ResponseEntity<Map<String, Object>> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse httpServletResponse
    ) {
        Authentication authentication = customAuthenticationProvider.authenticate(loginRequest.getUserLoginId(), loginRequest.getPassword());
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String refreshToken = userService.setRefreshToken(customUserDetail.getPK());
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        String accessToken = tokenProvider.createJwtAccessTokenByUser(customUserDetail.getPK(), new ArrayList<>(customUserDetail.getAuthorities()));
        httpServletResponse.addCookie(cookie);
        cookie.setSecure(true);
        return ResponseEntity.ok(ResponseFormat.responseTrue(accessToken));
    }

    @PostMapping("/v1/auth/logout")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> logout(
            HttpServletResponse httpServletResponse,
            @CookieValue(value = "refresh_token") String refreshToken
    ) {
        Cookie cookie = new Cookie("refresh_token", "");
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        userService.logout(refreshToken);
        return ResponseEntity.ok(ResponseFormat.responseTrue(null));
    }

    @GetMapping("/v1/auth/token")
    public ResponseEntity<Map<String, Object>> tokenIssuance(
            @CookieValue(value = "refresh_token") String refreshToken
    ) {
        User user = userService.getUserByRefreshToken(refreshToken);
        String newAccessToken = tokenProvider.createJwtAccessTokenByUser(user.getId(),  new ArrayList<>(user.getAuthorities()));
        return ResponseEntity.ok(ResponseFormat.responseTrue(newAccessToken));
    }

    @GetMapping("/v1/verify/crn")
    public ResponseEntity<Map<String, Object>> verifyCrn(
            @RequestParam(name = "crn") UserDto.Crn crn
    ) {
        userService.verifyCrnNumber(crn.getCrnLocate(), crn.getCrnDivision(), crn.getCrnUnique());
        return ResponseEntity.ok(ResponseFormat.responseTrue(null));
    }

    @GetMapping("/v1/verify/account")
    public ResponseEntity<Map<String, Object>> verifyAccount(
            @RequestParam(name = "id") String loginId
    ) {
        userService.verifyUserLoginId(loginId);
        return ResponseEntity.ok(ResponseFormat.responseTrue(null));
    }

    @GetMapping("/v1/verify/nickname")
    public ResponseEntity<Map<String, Object>> verifyNickname(
            @RequestParam(name = "nickname") String nickname) {
        userService.verifyUserNickname(nickname);
        return ResponseEntity.ok(ResponseFormat.responseTrue(null));
    }

    @PostMapping("/v1/user")
    public ResponseEntity<Map<String, Object>> signup(
            @RequestBody SignupRequest signupRequest
    ) {
        UserDto userDto = signupRequest.getUser();
        UserDto.Crn crn = userDto.getCrn();
        userService.signup(
                userDto.getUserLoginId(),
                crn.getCrnLocate(),
                crn.getCrnDivision(),
                crn.getCrnUnique(),
                userDto.getNickname(),
                userDto.getPhoneNum(),
                userDto.getPassword()
        );

        return ResponseEntity.ok(ResponseFormat.responseTrue(null));
    }

    @GetMapping("/v1/user")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> userProfile() {
        Long pk = CommonFunctions.getAuthUserPK();
        User user = userService.findByIdAndActiveUser(pk);
        return ResponseEntity.ok(ResponseFormat.responseTrue(new UserResponse(user)));
    }

    @DeleteMapping("/v1/user")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> deleteUser() {
        Long pk = CommonFunctions.getAuthUserPK();
        userService.disableUser(pk);
        return ResponseEntity.ok(ResponseFormat.responseTrue(null));
    }
}
