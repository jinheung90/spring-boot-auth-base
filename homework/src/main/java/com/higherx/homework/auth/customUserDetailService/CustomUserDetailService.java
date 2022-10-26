package com.higherx.homework.auth.customUserDetailService;


import com.higherx.homework.auth.userDetail.CustomUserDetail;
import com.higherx.homework.domain.user.entity.User;
import com.higherx.homework.domain.user.repository.UserRepository;
import com.higherx.homework.errorHandling.customRuntimeException.RuntimeExceptionWithCode;
import com.higherx.homework.errorHandling.errorEnums.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userLoginId) throws UsernameNotFoundException {

        CustomUserDetail detail = null;
        System.out.println(userLoginId);
        User user = userRepository.findByUserLoginId(userLoginId).orElseThrow(()-> new RuntimeExceptionWithCode(GlobalErrorCode.NOT_EXISTS_USER));
        return new CustomUserDetail(
            new ArrayList<>(user.getAuthorities()),
                user.getUserLoginId(),
            "",
                user.getId()
        );
    }
}
