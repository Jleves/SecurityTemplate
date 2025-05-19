package com.Security.demo.Repository;

import com.Security.demo.Model.RefreshToken;
import com.Security.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser_Id(Long id);

    boolean existsByUser(User user);
}
