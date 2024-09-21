package com.system.library.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.library.model.Role;
import com.system.library.util.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;


public class AddUserRequest {

    @NotBlank
    @JsonProperty("username")
    private String username;

    @NotBlank
    @JsonProperty("password")
    private String password;

    @Email
    @JsonProperty("email")
    private String email;

    @NotEmpty
    private Set<RoleEnum> roles = new HashSet<>();

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

    public Set<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEnum> roles) {
        this.roles = roles;
    }
}
