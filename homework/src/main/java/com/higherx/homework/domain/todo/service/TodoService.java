package com.higherx.homework.domain.todo.service;

import com.higherx.homework.domain.todo.entity.Todo;
import com.higherx.homework.domain.todo.repository.TodoRepository;
import com.higherx.homework.errorHandling.customRuntimeException.RuntimeExceptionWithCode;
import com.higherx.homework.errorHandling.errorEnums.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    public Page<Todo> getTodoListPage(Long userId, int page, int size) {
        return todoRepository.findAllByUserIdOrderByUpdatedAt(userId, PageRequest.of(page,size));
    }

    public Todo findTodoByUserIdAndId(Long userId, Long id) {
        return todoRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new RuntimeExceptionWithCode(GlobalErrorCode.BAD_REQUEST));
    }

    @Transactional
    public void updateTodoSuccess(Long userId, Long id) {
        Todo todo = findTodoByUserIdAndId(userId, id);
        todo.changeSuccessFlag();
        todoRepository.save(todo);
    }


    @Transactional
    public void updateTodo(String subject, String context, Long userId, Long id) {
        Todo todo = findTodoByUserIdAndId(userId, id);
        todo.updateTodo(subject, context);
        todoRepository.save(todo);
    }


    public void addTodo(String context, String subject, Long userId) {
        Todo todo = Todo.toEntity(userId, context, subject);
        todoRepository.save(todo);
    }

    public void disableTodo(Long userId, Long id) {
        Todo todo = findTodoByUserIdAndId(userId, id);
        todo.disableTodo();
        todoRepository.save(todo);
    }
}
