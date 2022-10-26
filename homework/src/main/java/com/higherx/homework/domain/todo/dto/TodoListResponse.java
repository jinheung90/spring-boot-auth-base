package com.higherx.homework.domain.todo.dto;

import com.higherx.homework.domain.todo.entity.Todo;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class TodoListResponse {
    private Page<TodoDto> todoPage;

    public TodoListResponse(Page<Todo> todo) {
        this.todoPage = todo.map(TodoDto::fromEntity);
    }
}
