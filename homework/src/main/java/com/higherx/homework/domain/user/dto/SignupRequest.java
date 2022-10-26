package com.higherx.homework.domain.user.dto;


import lombok.Getter;

import javax.validation.Valid;

@Getter
public class SignupRequest {
    @Valid
    private UserDto user;
}
