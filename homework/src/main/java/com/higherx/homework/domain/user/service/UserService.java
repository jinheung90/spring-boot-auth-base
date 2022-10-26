package com.higherx.homework.domain.user.service;

import com.higherx.homework.auth.TokenProvider;
import com.higherx.homework.domain.user.entity.User;
import com.higherx.homework.domain.user.repository.RefreshTokenRepository;
import com.higherx.homework.domain.user.repository.UserRepository;
import com.higherx.homework.errorHandling.customRuntimeException.RuntimeExceptionWithCode;
import com.higherx.homework.errorHandling.errorEnums.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(
            String userLoginId,
            Integer crnLocate,
            Integer crnDivision,
            Integer crnUnique,
            String nickname,
            String phoneNum,
            String password
    ) {
        this.verifyUserLoginId(userLoginId);
        this.verifyUserNickname(nickname);
        this.verifyCrnNumber(crnLocate,crnDivision,crnUnique);
        String encoded = passwordEncoder.encode(password);
        User user = User.toEntity(
                userLoginId,
                crnLocate,
                crnDivision,
                crnUnique,
                nickname,
                phoneNum,
                encoded,
                "ROLE_USER"
        );
        userRepository.save(user);
    }

    @Transactional
    public void disableUser(Long id) {
        User user = findByIdAndActiveUser(id);
        user.disableUser();
        userRepository.save(user);
    }

    public void verifyCrnNumber(Integer locate, Integer division, Integer unique) {
        if(this.existsUserByCrn(locate, division, unique)) {
            throw new RuntimeExceptionWithCode(GlobalErrorCode.ALREADY_EXISTS_CRN);
        }
    }
    public void verifyUserLoginId(String id) {
        if(this.existsUserByUserLoginId(id)) {
            throw new RuntimeExceptionWithCode(GlobalErrorCode.ALREADY_EXISTS_USER_ID);
        }
    }

    public void verifyUserNickname(String nickname) {
        if(this.existsUserByNickname(nickname)) {
            throw new RuntimeExceptionWithCode(GlobalErrorCode.ALREADY_EXISTS_NICKNAME);
        }
    }


    public User findByIdAndActiveUser(Long userId) {
        return userRepository.findByIdAndDeleted(userId, 0)
                .orElseThrow(() -> new RuntimeExceptionWithCode(GlobalErrorCode.NOT_EXISTS_USER));
    }

    private boolean existsUserByCrn(Integer locate, Integer division, Integer unique) {
        return userRepository.existsByCrnLocateAndCrnDivisionAndCrnUnique(locate, division,unique);
    }

    private boolean existsUserByUserLoginId(String loginId) {
        return userRepository.existsByUserLoginId(loginId);
    }

    private boolean existsUserByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public User getUserByRefreshToken(String inputRefreshToken) {
        String value = refreshTokenRepository.getIdByRefreshToken(inputRefreshToken);
        if(value == null) {
            throw new RuntimeExceptionWithCode(GlobalErrorCode.BAD_REQUEST);
        }
        return findByIdAndActiveUser(Long.valueOf(value));
    }

    public String setRefreshToken(Long userId) {
        return refreshTokenRepository.setRefreshToken(userId);
    }

    public void logout(String refreshToken) {
        refreshTokenRepository.deleteRefreshToken(refreshToken);
    }
}
