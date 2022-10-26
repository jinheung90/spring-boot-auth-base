package com.higherx.homework.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.higherx.homework.errorHandling.ErrorResponse;
import com.higherx.homework.errorHandling.errorEnums.GlobalErrorCode;
import com.higherx.homework.errorHandling.errorEnums.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        sendErrorMessage(response);
    }

    private void sendErrorMessage(HttpServletResponse res) throws IOException {
        res.setStatus(GlobalErrorCode.NOT_VALID_TOKEN.getStatus()); // 권한 부족 403
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        res.getWriter().write(this.objectMapper
                .writeValueAsString(ErrorResponse.responseBody(((IErrorCode) GlobalErrorCode.NOT_VALID_TOKEN).getCode(), "not allow this user not enough authority", HttpStatus.resolve(((IErrorCode) GlobalErrorCode.NOT_ALLOW_USER).getStatus()))));
    }
}
