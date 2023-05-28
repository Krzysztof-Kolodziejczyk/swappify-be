package com.example.swappify.service;

import com.example.swappify.model.entity.Authority;
import com.example.swappify.model.entity.Token;
import com.example.swappify.model.entity.User;
import com.example.swappify.repository.AuthorityRepository;
import com.example.swappify.repository.TokenRepository;
import com.example.swappify.repository.UserRepository;
import com.example.swappifyapimodel.model.dto.UpdateUserDetailsDTO;
import com.example.swappifyapimodel.model.dto.UserDTO;
import com.example.swappifyapimodel.model.exceptions.InvalidPasswordException;
import com.example.swappifyapimodel.model.exceptions.UserNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return new UserDTO(user.getEmail(), user.getUsername());
    }

    public void logout(String rawToken){
        var token = new Token();
        token.setToken(rawToken);
        token.setCreated(LocalDateTime.now());
        tokenRepository.save(token);
    }

    public UserDTO getUserDetails(@NonNull final String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return new UserDTO(user.getEmail(), user.getUsername());
    }

    @Transactional
    public void updateUser(final @NonNull String username, final @NonNull UpdateUserDetailsDTO toUpdate) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(toUpdate.getOldPassword(), user.getPassword().substring(8))){
            throw new InvalidPasswordException();
        }
        user.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(toUpdate.getNewPassword()));
        user.setEmail(toUpdate.getEmail());
    }
}
