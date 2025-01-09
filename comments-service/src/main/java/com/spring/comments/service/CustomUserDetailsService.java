package com.spring.comments.service;

import com.spring.comments.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    public CustomUserDetailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (token != null) {
                headers.add(HttpHeaders.AUTHORIZATION, token);
            }
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<User> response = restTemplate.exchange(
                    "http://AUTH/auth/user/validate",
                    HttpMethod.GET,
                    entity,
                    User.class
            );

            User user = response.getBody();
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Failed to authenticate user", e);
        }
    }
}