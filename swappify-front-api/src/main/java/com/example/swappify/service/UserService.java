package com.example.swappify.service;

import com.example.swappify.model.dto.UserDTO;
import com.example.swappify.model.entity.Authority;
import com.example.swappify.model.entity.Token;
import com.example.swappify.model.entity.User;
import com.example.swappify.repository.AuthorityRepository;
import com.example.swappify.repository.TokenRepository;
import com.example.swappify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final TokenRepository tokenRepository;

    public UserDTO createUser(User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return null;
        }
        user.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword()));
        var authority = new Authority();
        authority.setAuthority("ROLE_USER");
        authority.setUsername(user);
        authorityRepository.save(authority);
        return new UserDTO(user.getFirstName(), user.getLastName(), user.getUsername());
    }

    public void logout(String rawToken){
        var token = new Token();
        token.setToken(rawToken);
        token.setCreated(LocalDateTime.now());
        tokenRepository.save(token);
    }
}
