package com.system.library.mapper;

import com.system.library.dto.user.UserDTO;
import com.system.library.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}

