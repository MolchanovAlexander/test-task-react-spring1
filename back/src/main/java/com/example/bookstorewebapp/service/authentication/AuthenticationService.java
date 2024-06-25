package com.example.bookstorewebapp.service.authentication;

import com.example.bookstorewebapp.dto.user.CreateUserRequestDto;
import com.example.bookstorewebapp.dto.user.UserLoginRequestDto;
import com.example.bookstorewebapp.dto.user.UserLoginResponseDto;
import com.example.bookstorewebapp.dto.user.UserResponseDto;
import com.example.bookstorewebapp.model.User;

public interface AuthenticationService {
    UserResponseDto create(CreateUserRequestDto requestDto);

    UserLoginResponseDto authenticate(UserLoginRequestDto requestDto);

    User findById(Long id);
}
