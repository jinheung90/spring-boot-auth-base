package com.higherx.homework.domain.todo.dto;

import com.higherx.homework.domain.todo.entity.Todo;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Builder
@Getter
public class TodoDto {
    private Long id;
    @NotEmpty
    private String context;
    @NotEmpty
    private String subject;
    private Integer success;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TodoDto fromEntity(Todo todo) {
        return TodoDto.builder()
                .context(todo.getContext())
                .subject(todo.getSubject())
                .success(todo.getSuccess())
                .id(todo.getId())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
