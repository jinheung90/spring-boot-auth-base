package com.higherx.homework.domain.todo.dto;

import com.higherx.homework.domain.todo.entity.Todo;
import lombok.Getter;

@Getter
public class TodoResponse {
    private TodoDto todo;
    public TodoResponse(Todo todo) {
        this.todo = TodoDto.fromEntity(todo);
    }
}
