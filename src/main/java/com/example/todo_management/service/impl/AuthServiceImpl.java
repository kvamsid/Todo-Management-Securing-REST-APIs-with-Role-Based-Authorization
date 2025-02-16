package com.example.todo_management.service.impl;

import com.example.todo_management.dto.LoginDto;
import com.example.todo_management.dto.RegisterDto;
import com.example.todo_management.entity.Role;
import com.example.todo_management.entity.User;
import com.example.todo_management.exception.TodoAPIException;
import com.example.todo_management.repository.RoleRepository;
import com.example.todo_management.repository.UserRepository;
import com.example.todo_management.security.JwtTokenProvider;
import com.example.todo_management.service.AuthService;
import io.jsonwebtoken.Jwt;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {

        //Check if username already exists in the Database.
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists.");
        }
        //Check if email already exists in the Database.
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email already exists.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> userRoles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER");
        userRoles.add(role);
        user.setRoles(userRoles);

        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }
}
