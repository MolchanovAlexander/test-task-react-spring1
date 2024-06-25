package com.example.bookstorewebapp.dto.user;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginResponseDto(
        @NotEmpty
        String token
) {
}
