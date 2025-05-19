package com.Security.demo.Controller;

import com.Security.demo.Model.Auth.AuthResponse;
import com.Security.demo.Model.Auth.LoginRequest;
import com.Security.demo.Model.Auth.TokenRefreshRequest;
import com.Security.demo.Model.Auth.TokenRefreshResponse;
import com.Security.demo.Model.User;
import com.Security.demo.Security.JWTUtil;
import com.Security.demo.Service.Auth.AuthService;
import com.Security.demo.Service.Auth.RefreshTokenService;
import com.Security.demo.Service.UserService;
import com.Security.demo.Utils.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AppProperties appProperties;
    private RefreshTokenService refreshTokenService;
@Autowired
    public AuthController(AuthService authService, UserService userService, JWTUtil jwtUtil, AppProperties appProperties, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.appProperties = appProperties;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        System.out.println("Controlador login:  " + loginRequest);

        return ResponseEntity.ok(authService.login(loginRequest));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestToken = request.getRefreshToken();


        return refreshTokenService.findByToken(requestToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> {
                    // Eliminamos o invalidamos el token usado
                    refreshTokenService.delete(refreshToken.getUser().getId());

                    // Creamos nuevos tokens
                    User user = refreshToken.getUser();
                    UserDetails userDetails = user;

                    String newAccessToken = jwtUtil.generateToken(userDetails);
                    String newRefreshToken = refreshTokenService.createRefreshToken(user.getUsername()).getToken();

                    return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, newRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));
    }


    @GetMapping("/invitado")
    public String invitado(){
    return "Hola soy invitado";
    }

    @GetMapping("/user")
    public String user(){
        return "Hola soy user";
    }

    @GetMapping("/administrador")
    public String administrador(){
        return "Hola soy administrador";
    }

    @GetMapping("/superadmin")
    public String superadmin(){
        return "Hola soy superadmin ";
    }


}


/*

Con un solo refresh token
@PostMapping("/refresh-token")
public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
    String requestToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestToken)
            .map(refreshTokenService::verifyExpiration)
            .map(RefreshToken::getUser)
            .map(user -> {
                UserDetails userDetails = new CustomUserDetails(user); // o como tengas implementado
                String newAccessToken = jwtUtil.generateToken(userDetails);
                return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, requestToken));
            })
            .orElseThrow(() -> new RuntimeException("Refresh token inválido"));
}


Con rotacion:
@PostMapping("/refresh-token")
public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
    String requestToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestToken)
            .map(refreshTokenService::verifyExpiration)
            .map(refreshToken -> {
                // Eliminamos o invalidamos el token usado
                refreshTokenService.delete(refreshToken);

                // Creamos nuevos tokens
                User user = refreshToken.getUser();
                UserDetails userDetails = new CustomUserDetails(user);

                String newAccessToken = jwtUtil.generateToken(userDetails);
                String newRefreshToken = refreshTokenService.createRefreshToken(user.getUsername(), Duration.ofHours(2)); // por ejemplo, 2 horas

                return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, newRefreshToken));
            })
            .orElseThrow(() -> new RuntimeException("Refresh token inválido"));
}


 */