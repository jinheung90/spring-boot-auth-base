package com.higherx.homework.commons;

import com.higherx.homework.auth.userDetail.CustomUserDetail;
import com.higherx.homework.errorHandling.customRuntimeException.RuntimeExceptionWithCode;
import com.higherx.homework.errorHandling.errorEnums.GlobalErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Random;

public class CommonFunctions {
    public static String generateUpperLettersAndNum(int size) {
        final int leftLimit = 48; // numeral '0'
        final int rightLimit = 90; // letter 'z'
        final int targetStringLength = size;

        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> ((i >= 48 && i <= 57) || (i >= 65 && i <= 90))) // 10 이후의 특수문자 제거 문자 사이의 특수문자 제거
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static Long getAuthUserPK() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal == null) {
            throw new RuntimeExceptionWithCode(GlobalErrorCode.NOT_EXISTS_USER);
        }
        User user = (User) principal;
        return Long.valueOf(user.getUsername());
    }
}

