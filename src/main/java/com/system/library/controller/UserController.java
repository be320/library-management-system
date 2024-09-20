package com.system.library.controller;

import com.system.library.config.annotations.IsUser;
import com.system.library.dto.user.*;
import com.system.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest)  {
        RegisterResponse registerResponse = userService.register(registerRequest);
        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws NoSuchAlgorithmException, InvalidKeyException {
        LoginResponse loginResponse = userService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/me")
    @IsUser
    public ResponseEntity<UserDTO> viewUserDetails(){
        UserDTO userDTO = userService.viewUserDetails();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/me")
    @IsUser
    public ResponseEntity<UserDTO> updateUserDetails(@RequestBody UpdateUserRequest updateUserRequest){
        UserDTO userDTO = userService.updateUserDetails(updateUserRequest);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @DeleteMapping("/me")
    @IsUser
    public ResponseEntity<String> deleteUserDetails(){
        userService.deleteUserDetails();
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

}
