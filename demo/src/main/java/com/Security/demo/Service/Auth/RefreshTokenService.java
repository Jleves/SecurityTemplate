package com.Security.demo.Service.Auth;

import com.Security.demo.Model.RefreshToken;
import com.Security.demo.Model.User;
import com.Security.demo.Repository.RefreshTokenRepository;
import com.Security.demo.Repository.UserRepository;
import com.Security.demo.Utils.AppProperties;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {


    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    private AppProperties appProperties;

    @Autowired
    private UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, AppProperties appProperties, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.appProperties = appProperties;
        this.userRepository = userRepository;
    }
@Transactional
    public RefreshToken createRefreshToken(String username, Long id) {

        User user = userRepository.findByUsername(username).orElseThrow();
        refreshTokenRepository.deleteByUser_Id(user.getId());
        Long expirationHours= appProperties.getSecurity().getJwt().getRefreshExpirationHours();
        System.out.println("Expiracion hours:  " + expirationHours);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(expirationHours, ChronoUnit.HOURS));
        System.out.println("Hora actual: " + LocalDateTime.now());

        System.out.println("Zona horaria actual: " + ZoneId.systemDefault());

        System.out.println(" Refresh token que val repositorio:  ----->  "+ refreshToken.getExpiryDate());
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expirado.");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void delete(Long userId) {
        refreshTokenRepository.deleteByUser_Id(userId);
    }

   /* public boolean existsByUser(Long id){
       return refreshTokenRepository.existsByUserId(id);
    }*/
}
