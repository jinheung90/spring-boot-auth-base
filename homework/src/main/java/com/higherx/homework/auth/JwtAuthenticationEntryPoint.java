package com.higherx.homework.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.higherx.homework.errorHandling.ErrorResponse;
import com.higherx.homework.errorHandling.errorEnums.GlobalErrorCode;
import com.higherx.homework.errorHandling.errorEnums.IErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        sendErrorMessage(response);
    }

    private void sendErrorMessage(HttpServletResponse res) throws IOException {
        res.setStatus(GlobalErrorCode.NOT_VALID_TOKEN.getStatus()); // 인가 부족 401
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        res.getWriter().write(this.objectMapper
                .writeValueAsString( ErrorResponse.responseBody(((IErrorCode) GlobalErrorCode.NOT_VALID_TOKEN).getCode(), "not valid token", HttpStatus.resolve(((IErrorCode) GlobalErrorCode.NOT_VALID_TOKEN).getStatus()))));
    }
}
