package com.higherx.homework.domain.user.dto;

import com.higherx.homework.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    private Long id;
    @NotBlank
    private String userLoginId;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    @NotBlank
    private String phoneNum;
    @Valid
    private Crn crn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter
    @NoArgsConstructor
    public static class Crn {
        @NotNull
        private Integer crnLocate;
        @NotNull
        private Integer crnDivision;
        @NotNull
        private Integer crnUnique;

        public Crn(User user) {
            this.crnLocate = user.getCrnLocate();
            this.crnDivision = user.getCrnDivision();
            this.crnUnique = user.getCrnUnique();
        }
    }

    public static UserDto fromEntityWhenMyPage(User user) {
        return UserDto
                .builder()
                .password("")
                .id(user.getId())
                .nickname(user.getNickname())
                .userLoginId(user.getUserLoginId())
                .phoneNum(user.getPhoneNum())
                .crn(new Crn(user))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }



}
