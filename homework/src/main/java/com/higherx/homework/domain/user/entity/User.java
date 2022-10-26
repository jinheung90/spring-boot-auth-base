
package com.higherx.homework.domain.user.entity;



import com.higherx.homework.domain.todo.entity.Todo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User
 */

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true, name = "user_login_id")
    private String userLoginId = "";

    @Column(name = "crn_locate")
    private Integer crnLocate;

    @Column(name = "crn_division")
    private Integer crnDivision;

    @Column(name = "crn_unique")
    private Integer crnUnique;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String phoneNum;

    @Column
    private String password = "";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_authorities",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)
            )},
            inverseJoinColumns = {
                    @JoinColumn(name = "authority_name", referencedColumnName = "authority_name",
                            foreignKey =  @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))})
    private List<Authority> authorities;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime disabledAt;

    @Column(name = "is_deleted", columnDefinition = "integer DEFAULT 0")
    private Integer deleted = 0;

    public void disableUser() {
        this.deleted = 1;
        this.disabledAt = LocalDateTime.now();
    }
    public static User toEntity(
            String userLoginId,
            Integer crnLocate,
            Integer crnDivision,
            Integer crnUnique,
            String nickname,
            String phoneNum,
            String password,
            String... authorities
    ) {
        return User.builder()
                .authorities(Arrays.stream(authorities).map(Authority::new).collect(Collectors.toList()))
                .userLoginId(userLoginId)
                .password(password)
                .deleted(0)
                .crnLocate(crnLocate)
                .crnDivision(crnDivision)
                .crnUnique(crnUnique)
                .nickname(nickname)
                .phoneNum(phoneNum)
                .build();
    }
}
