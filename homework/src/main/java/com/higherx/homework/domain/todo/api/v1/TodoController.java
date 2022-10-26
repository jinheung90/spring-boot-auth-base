package com.higherx.homework.domain.todo.api.v1;

import com.higherx.homework.auth.userDetail.CustomUserDetail;
import com.higherx.homework.commons.CommonFunctions;
import com.higherx.homework.commons.ResponseFormat;
import com.higherx.homework.domain.todo.dto.TodoDto;
import com.higherx.homework.domain.todo.dto.TodoListResponse;
import com.higherx.homework.domain.todo.dto.TodoRequest;
import com.higherx.homework.domain.todo.dto.TodoResponse;
import com.higherx.homework.domain.todo.entity.Todo;
import com.higherx.homework.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v1/todo")
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> getTodoList(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
            ) {
        Long pk = CommonFunctions.getAuthUserPK();
        Page<Todo> list = todoService.getTodoListPage(pk, page, size);
        return ResponseEntity.ok(ResponseFormat.responseTrue(new TodoListResponse(list)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> getTodoDetail(
            @PathVariable Long id
    ) {
        Long pk = CommonFunctions.getAuthUserPK();
        Todo todo = todoService.findTodoByUserIdAndId(pk, id);
        return ResponseEntity.ok(ResponseFormat.responseTrue(new TodoResponse(todo)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> addTodo(
           @RequestBody @Valid TodoRequest todoRequest
    ) {
        TodoDto todoDto = todoRequest.getTodo();
        Long pk = CommonFunctions.getAuthUserPK();
        todoService.addTodo(todoDto.getContext(),todoDto.getSubject(),pk);
        return ResponseEntity.ok(ResponseFormat.responseTrue(null));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> deleteTodo(
            @PathVariable Long id
    ) {
        Long pk = CommonFunctions.getAuthUserPK();
        todoService.disableTodo(pk, id);
        return ResponseEntity.ok(ResponseFormat.responseTrue(null));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Map<String, Object>> updateTodo(
            @RequestBody TodoRequest todoRequest,
            @PathVariable Long id

    ) {
        Long pk = CommonFunctions.getAuthUserPK();
        TodoDto todoDto = todoRequest.getTodo();
        todoService.updateTodo(todoDto.getSubject(), todoDto.getContext(), pk, id);
        return ResponseEntity.ok(ResponseFormat.responseTrue(null));
    }

    @GetMapping("/{id}/check")
    public ResponseEntity<Map<String, Object>> todoSuccessCheck(
            @PathVariable Long id

    ) {
        Long pk = CommonFunctions.getAuthUserPK();
        todoService.updateTodoSuccess(pk, id);
        return ResponseEntity.ok(ResponseFormat.responseTrue(null));
    }
}
