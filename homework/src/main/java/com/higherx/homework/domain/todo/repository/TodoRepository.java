package com.higherx.homework.domain.todo.repository;

import com.higherx.homework.domain.todo.entity.Todo;

import com.higherx.homework.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findAllByUserIdOrderByUpdatedAt(Long userId, Pageable pageable);
    Optional<Todo> findByUserIdAndId(Long userId, Long id);

}
