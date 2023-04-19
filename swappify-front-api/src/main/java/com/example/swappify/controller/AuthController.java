package com.example.swappify.controller;

import com.example.swappifyauthconnector.connector.AuthConnector;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
public class AuthController implements AuthConnector {

    @Override
    public ResponseEntity<Void> checkAuthStatus(@NonNull String authToken) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
