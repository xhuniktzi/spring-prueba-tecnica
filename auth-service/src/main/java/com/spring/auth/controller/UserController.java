package com.spring.auth.controller;

import com.spring.auth.model.User;
import com.spring.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth/user")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/validate")
    public ResponseEntity<User> validate(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User current = (User) authentication.getPrincipal();
        if (authService.findUserByUsername(current.getUsername()).isEnabled()){
            current.setPassword(null);
            return ResponseEntity.ok(current);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
