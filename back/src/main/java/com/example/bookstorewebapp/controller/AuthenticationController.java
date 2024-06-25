package com.example.bookstorewebapp.controller;

import com.example.bookstorewebapp.dto.user.CreateUserRequestDto;
import com.example.bookstorewebapp.dto.user.UserLoginRequestDto;
import com.example.bookstorewebapp.dto.user.UserLoginResponseDto;
import com.example.bookstorewebapp.dto.user.UserResponseDto;
import com.example.bookstorewebapp.service.authentication.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User management", description = "Endpoints for managing book")
@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "User login", description = "Login user.")
    @PostMapping("/login")
    public UserLoginResponseDto loginUser(@Valid @RequestBody UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @Operation(summary = "Create user", description = "create user entity from request body")
    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponseDto createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        return authenticationService.create(requestDto);
    }
}
