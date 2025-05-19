package com.Security.demo.Model.Auth;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequest {

        private String refreshToken;

        public String getRefreshToken() {
                return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
                this.refreshToken = refreshToken;
        }
}
