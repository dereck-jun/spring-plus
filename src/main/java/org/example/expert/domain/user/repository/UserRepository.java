package org.example.expert.domain.user.repository;

import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select new org.example.expert.domain.user.dto.response.UserResponse(u.id, u.email) "
        + "from User u "
        + "where u.nickname = :nickname")
    Page<UserResponse> findAllByNickname(String nickname, Pageable pageable);
    boolean existsByEmail(String email);
}
