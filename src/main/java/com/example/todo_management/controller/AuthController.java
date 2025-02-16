package com.example.todo_management.controller;


import com.example.todo_management.dto.JwtAuthResponse;
import com.example.todo_management.dto.LoginDto;
import com.example.todo_management.dto.RegisterDto;
import com.example.todo_management.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody LoginDto loginDto) {
        String accessToken = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(accessToken);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

}
