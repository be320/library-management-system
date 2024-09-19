package com.system.library.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public class LoginResponse {

    @NotNull
    @JsonProperty("username")
    private String username;

    @NotNull
    @JsonProperty("token")
    private String token;

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
