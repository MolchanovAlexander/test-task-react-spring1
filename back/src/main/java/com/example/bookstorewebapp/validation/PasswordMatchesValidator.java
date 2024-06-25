package com.example.bookstorewebapp.validation;

import com.example.bookstorewebapp.dto.user.CreateUserRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<FieldMatch, CreateUserRequestDto> {

    @Override
    public boolean isValid(CreateUserRequestDto requestDto,
                           ConstraintValidatorContext context) {
        return requestDto.getPassword() != null
                && requestDto.getPassword().equals(requestDto.getRepeatedPassword());
    }
}
