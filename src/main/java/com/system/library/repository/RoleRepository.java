package com.system.library.repository;

import com.system.library.model.Role;
import com.system.library.model.User;
import com.system.library.util.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);
}
