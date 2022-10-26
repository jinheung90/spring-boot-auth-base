package com.higherx.homework.domain.user.repository;


import com.higherx.homework.commons.CommonFunctions;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {


    private final RedisTemplate<String, String> redisTemplate;
    private static final String token_prefix = "refresh:";
    private static final String pk_prefix = "pk:";
    private static final Integer token_size = 20;

    @Value("${token.refresh-token-expired}")
    private Integer refresh_token_expired_days;

    public String setRefreshToken(Long userId) {

        String tokenByPk = redisTemplate.opsForValue().get(pk_prefix + userId.toString());
        if(tokenByPk != null) {
            redisTemplate.opsForValue().set(token_prefix + tokenByPk, "", 1, TimeUnit.MICROSECONDS);
            redisTemplate.opsForValue().set(pk_prefix + userId.toString(), "", 1, TimeUnit.MICROSECONDS);
        }
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token_prefix + token, userId.toString(), refresh_token_expired_days, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(pk_prefix + userId.toString(), token, refresh_token_expired_days, TimeUnit.DAYS);
        return token;
    }

    public String getIdByRefreshToken(String token) {
        return redisTemplate.opsForValue().get(token_prefix + token);
    }

    public void deleteRefreshToken(String token) {
        String pkByToken = redisTemplate.opsForValue().get(token_prefix + token);

        if(pkByToken != null) {
            redisTemplate.opsForValue().set(token_prefix + token, "",1, TimeUnit.MICROSECONDS);
            redisTemplate.opsForValue().set(pk_prefix + pkByToken, "", 1, TimeUnit.MICROSECONDS);
        }
    }
}
