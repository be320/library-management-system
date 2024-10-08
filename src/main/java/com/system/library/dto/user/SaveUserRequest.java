package com.system.library.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


public class SaveUserRequest {

    @NotBlank
    @JsonProperty("username")
    private String username;

    @NotBlank
    @JsonProperty("password")
    private String password;

    @Email
    @JsonProperty("email")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
