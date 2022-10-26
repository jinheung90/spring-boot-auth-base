package com.higherx.homework.domain.user.dto;


import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank
    private String userLoginId;
    @NotBlank
    private String password;
}
