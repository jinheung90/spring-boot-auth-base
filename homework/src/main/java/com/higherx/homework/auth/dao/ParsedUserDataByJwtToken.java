package com.higherx.homework.auth.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class ParsedUserDataByJwtToken {
    private Long userId;
    private List<GrantedAuthority> authorities;
    private Date expiration;
}
