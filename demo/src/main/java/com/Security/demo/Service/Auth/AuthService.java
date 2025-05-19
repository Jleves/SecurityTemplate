package com.Security.demo.Service.Auth;

import com.Security.demo.Dto.UserDTO;
import com.Security.demo.Model.Auth.AuthResponse;
import com.Security.demo.Model.Auth.LoginRequest;
import com.Security.demo.Model.User;
import com.Security.demo.Repository.UserRepository;
import com.Security.demo.Security.JWTUtil;
import com.Security.demo.Security.PasswordEncoder;
import com.Security.demo.Utils.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;  //Para buscar el usuario
    private final JWTUtil jwtUtil;  //Para generar el token
    private final PasswordEncoder passwordEncoder; //Encriptar el TOKEN

    private RefreshTokenService refreshTokenService;
    private AppProperties appProperties;
@Autowired
    public AuthService(UserRepository userRepository, JWTUtil jwtUtil, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService, AppProperties appProperties, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.appProperties = appProperties;
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private final AuthenticationManager authenticationManager; // Para que se autentique
    public AuthResponse login(LoginRequest loginRequest) {

        try {

            System.out.println("Auth Service: --->  username: " + loginRequest.getUsername() +"  Password:  "+ loginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));


            // ya autenticado por Spring Security.
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();


            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow();

            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setRol(user.getRol().getAuthority());

            String token = jwtUtil.generateToken(user);

            System.out.println("User detal antes de crear el refresh token :   --->    " + userDetails.getUsername());
            String refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername()).getToken();
            System.out.println("Refresh token :   --->    " + refreshToken);
            Long expiration= appProperties.getSecurity().getJwt().getAccessExpirationMinutes()*60;



            return AuthResponse.builder()
                    .accessToken(token)
                    .refreshToken(refreshToken)
                    .tokenType("Bearer")
                    .expiresIn(expiration)
                    .user(userDTO)
                    .build();


        }catch (AuthenticationException ex) {
            throw new RuntimeException("Password o usuario incorrecto");

        }


    }
}
/*

 System.out.println("Auth Service: --->  username: " + loginRequest.getUsername() +"  Pin:  "+ loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRol(user.getRol().getAuthority());

        String token = jwtUtil.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .expiresIn(3600)
                .user(userDTO)
                .build();

 */