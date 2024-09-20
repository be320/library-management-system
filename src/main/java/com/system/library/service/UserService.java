package com.system.library.service;

import com.system.library.dto.user.*;
import com.system.library.exception.EntityAlreadyExistingException;
import com.system.library.exception.EntityNotFoundException;
import com.system.library.exception.InvalidPasswordException;
import com.system.library.mapper.UserMapper;
import com.system.library.model.User;
import com.system.library.model.Role;
import com.system.library.model.User;
import com.system.library.repository.RoleRepository;
import com.system.library.repository.UserRepository;
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

    @Autowired
    UserMapper userMapper;

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

    public UserDTO viewUserDetails(){

        String username =  tokenService.getUsernameFromToken();
        Optional<User> user =  userRepository.findByUsername(username);
        if(user.isEmpty())
            throw new EntityNotFoundException();

        return userMapper.toDTO(user.get());
    }

    public UserDTO updateUserDetails(UpdateUserRequest updateUserRequest){

        String username =  tokenService.getUsernameFromToken();
        Optional<User> userOptional =  userRepository.findByUsername(username);

        if(userOptional.isPresent()){
            User user = userOptional.get();

            if(updateUserRequest.getEmail() != null)
                user.setEmail(updateUserRequest.getEmail());
            if(updateUserRequest.getUsername() != null)
                user.setUsername(updateUserRequest.getUsername());
            if(updateUserRequest.getPassword() != null)
                user.setPassword(updateUserRequest.getPassword());

            userRepository.save(user);
            return userMapper.toDTO(user);
        }
        else {
            throw new EntityNotFoundException();
        }

    }

    public void deleteUserDetails(){

        String username =  tokenService.getUsernameFromToken();
        Optional<User> user =  userRepository.findByUsername(username);
        if(user.isEmpty())
            throw new EntityNotFoundException();

        userRepository.delete(user.get());
    }

    private String generateToken(String username, RoleEnum role) throws NoSuchAlgorithmException, InvalidKeyException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "web");
        claims.put("roles", role);
        String token =  tokenService.generateToken(claims, username);
        return token;
    }

    private void validatePassword(String rawPassword, String encodedPassword, String username) {
        if(!passwordEncoder.matches(rawPassword, encodedPassword)){
            log.error("User: {} password is wrong", username);
            throw new InvalidPasswordException();
        }
    }

}
