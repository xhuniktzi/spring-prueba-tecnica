package com.spring.auth.service;

import com.spring.auth.dto.UserDto;
import com.spring.auth.model.User;
import com.spring.auth.repository.IUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(IUserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User createUser(UserDto user) {
         Optional<User> located = userRepository.findByUsername(user.getUsername());

         if (located.isPresent()) {
             throw new IllegalArgumentException("Username already exists");
         } else {
             User userEntity = new User();
             userEntity.setUsername(user.getUsername());
             userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
             userEntity.setEnabled(true);
             User saved = userRepository.save(userEntity);
             saved.setPassword(null);
             return saved;
         }
    }

    public User authenticate(UserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }

}
