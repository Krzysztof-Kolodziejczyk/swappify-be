package com.example.swappify.controller;


import com.example.swappify.model.entity.User;
import com.example.swappify.service.UserService;
import com.example.swappifyapimodel.model.dto.UpdateUserDetailsDTO;
import com.example.swappifyapimodel.model.dto.UserDTO;
import com.example.swappifyapimodel.model.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService service;

    @GetMapping("/signout")
    public void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        service.logout(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody User user) {
        return Optional.ofNullable(service.createUser(user))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/details")
    public UserDTO getUserDetails(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.getUserDetails(username);
    }

    @PostMapping("/update")
    public void updatePssword(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                              @RequestBody UpdateUserDetailsDTO toUpdate){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        service.updateUser(username, toUpdate);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundError() {
        return "No user with such a username";
    }
}
