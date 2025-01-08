package com.spring.auth.controller;

import com.spring.auth.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/validate")
    public ResponseEntity<User> validate(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User current = (User) authentication.getPrincipal();
        current.setPassword(null);
        return ResponseEntity.ok(current);
    }
}
