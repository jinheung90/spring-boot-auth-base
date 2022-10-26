package com.higherx.homework.domain.user.repository;

import com.higherx.homework.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Long countByDeleted(Integer deleted);
    Optional<User> findByUserLoginId(String userLoginId);
    Optional<User> findByIdAndDeleted(Long id, Integer deleted);
    Boolean existsByCrnLocateAndCrnDivisionAndCrnUnique(Integer locate, Integer division, Integer unique);
    Boolean existsByUserLoginId(String userLoginId);
    Boolean existsByNickname(String nickname);

}
