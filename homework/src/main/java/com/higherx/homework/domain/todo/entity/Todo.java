package com.higherx.homework.domain.todo.entity;

import com.higherx.homework.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "todo_list")
@Entity
@Getter
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(length = 2046)
    private String context;

    @Column
    private String subject;

    @Column(name = "success", columnDefinition = "integer DEFAULT 0")
    private Integer success;

    @Column(name = "user_id")
    private Long userId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime disabledAt;

    @Column(name = "is_deleted", columnDefinition = "integer DEFAULT 0")
    private Integer deleted;

    public void disableTodo() {
        this.deleted = 1;
        this.disabledAt = LocalDateTime.now();
    }

    public void changeSuccessFlag() {
        if(success.equals(1)) {
            success = 0;
        } else if(success.equals(0)) {
            success = 1;
        } else {
            success = 0;
        }
    }

    public void setContext(String context) {
        this.context = Objects.requireNonNullElse(context, this.context);

    }

    public static Todo toEntity(Long userId, String context, String subject) {
        return Todo.builder().userId(userId).success(0).context(context).subject(subject).build();
    }
}
