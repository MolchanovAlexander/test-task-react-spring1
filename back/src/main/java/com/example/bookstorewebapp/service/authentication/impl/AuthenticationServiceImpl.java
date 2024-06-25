package com.example.bookstorewebapp.service.authentication.impl;

import com.example.bookstorewebapp.dto.user.CreateUserRequestDto;
import com.example.bookstorewebapp.dto.user.UserLoginRequestDto;
import com.example.bookstorewebapp.dto.user.UserLoginResponseDto;
import com.example.bookstorewebapp.dto.user.UserResponseDto;
import com.example.bookstorewebapp.exception.EntityNotFoundException;
import com.example.bookstorewebapp.exception.RegistrationException;
import com.example.bookstorewebapp.mapper.UserMapper;
import com.example.bookstorewebapp.model.Role;
import com.example.bookstorewebapp.model.User;
import com.example.bookstorewebapp.repository.role.RoleRepository;
import com.example.bookstorewebapp.repository.user.UserRepository;
import com.example.bookstorewebapp.security.JwtUtil;
import com.example.bookstorewebapp.service.authentication.AuthenticationService;
import com.example.bookstorewebapp.service.shoppingcart.ShoppingCartService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ShoppingCartService shoppingCartService;

    @Override
    public UserResponseDto create(CreateUserRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("User already exists with this email: "
                    + requestDto.getEmail());
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Role defaultRole = roleRepository.findByRole(Role.RoleName.USER);
        user.setRoles(Collections.singleton(defaultRole));
        User userWithId = userRepository.save(user);
        shoppingCartService.createShoppingCart(userWithId);
        return userMapper.toDto(userWithId);
    }

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.email(), requestDto.password())
        );
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no user with id:" + id)
        );
    }
}
