package com.higherx.homework.domain.user.dto;

import com.higherx.homework.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private UserDto user;
    public UserResponse(User entity) {
        user = UserDto.fromEntityWhenMyPage(entity);
    }
}
