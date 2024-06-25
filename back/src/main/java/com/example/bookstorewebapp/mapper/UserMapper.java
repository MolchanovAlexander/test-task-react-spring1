package com.example.bookstorewebapp.mapper;

import com.example.bookstorewebapp.config.MapperConfig;
import com.example.bookstorewebapp.dto.user.CreateUserRequestDto;
import com.example.bookstorewebapp.dto.user.UserResponseDto;
import com.example.bookstorewebapp.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(CreateUserRequestDto requestDto);
}
