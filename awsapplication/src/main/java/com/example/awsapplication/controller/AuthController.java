package com.example.awsapplication.controller;

import com.example.awsapplication.auth.JwtUtil;
import com.example.awsapplication.data.JwtResponse;
import com.example.awsapplication.data.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        if ("admin".equals(request.getUsername())
                && "password".equals(request.getPassword())) {

            String token = jwtUtil.generateToken(
                    request.getUsername(),
                    List.of("ROLE_ADMIN")
            );

            return ResponseEntity.ok(new JwtResponse(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}

