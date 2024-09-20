package com.system.library.service;

import com.system.library.dto.user.LoginRequest;
import com.system.library.dto.user.LoginResponse;
import com.system.library.dto.user.RegisterRequest;
import com.system.library.dto.user.RegisterResponse;
import com.system.library.exception.EntityAlreadyExistingException;
import com.system.library.exception.EntityNotFoundException;
import com.system.library.exception.InvalidPasswordException;
import com.system.library.model.Role;
import com.system.library.model.User;
import com.system.library.repository.RoleRepository;
import com.system.library.repository.UserRepository;
import com.system.library.util.SecurityUtil;
import com.system.library.util.enums.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest loginRequest) throws NoSuchAlgorithmException, InvalidKeyException {

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if(user.isEmpty())
            throw new EntityNotFoundException();
        else{
            validatePassword(loginRequest.getPassword(), user.get().getPassword(), loginRequest.getUsername());
        }

        String token = generateToken(loginRequest.getUsername(), RoleEnum.ROLE_USER);
        return new LoginResponse(loginRequest.getUsername(), token);
    }



    public RegisterResponse register(RegisterRequest registerRequest) {

        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new EntityAlreadyExistingException();
        }
        else {
            String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
            Set<Role> userRoles = new HashSet<>();
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER).get();
            userRoles.add(userRole);
            userRepository.save(new User(registerRequest.getUsername(), hashedPassword, registerRequest.getEmail(), userRoles));
        }
        return new RegisterResponse(registerRequest.getUsername());
    }

    private String generateToken(String username, RoleEnum role) throws NoSuchAlgorithmException, InvalidKeyException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "web");
        claims.put("roles", role);
        String token =  tokenService.generateToken(claims, username);
        String hmacTokenString = SecurityUtil.hmacSHA256(SecurityUtil.secretHmac, token);
        return token;
    }

    private void validatePassword(String rawPassword, String encodedPassword, String username) {
        if(!passwordEncoder.matches(rawPassword, encodedPassword)){
            log.error("User: {} password is wrong", username);
            throw new InvalidPasswordException();
        }
    }

}
