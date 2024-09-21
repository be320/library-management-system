package com.system.library.dto.user;

import com.system.library.dto.user.UserDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;


public class ViewUsersResponse {

    @NotNull
    private List<UserDTO> users;

    public ViewUsersResponse(List<UserDTO> users) {
        this.users = users;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
