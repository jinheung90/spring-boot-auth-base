package com.higherx.homework.domain.todo.dto;

import lombok.Getter;

import javax.validation.Valid;

@Getter
public class TodoRequest {
    @Valid
    private TodoDto todo;
}
