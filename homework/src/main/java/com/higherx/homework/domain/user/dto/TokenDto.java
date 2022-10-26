package com.higherx.homework.domain.user.dto;

import lombok.Getter;
import lombok.Builder;

@Getter
@Builder
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
