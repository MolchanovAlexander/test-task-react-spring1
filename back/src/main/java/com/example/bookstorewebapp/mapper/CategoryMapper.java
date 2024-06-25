package com.example.bookstorewebapp.mapper;

import com.example.bookstorewebapp.config.MapperConfig;
import com.example.bookstorewebapp.dto.category.CategoryResponseDto;
import com.example.bookstorewebapp.dto.category.CreateCategoryRequestDto;
import com.example.bookstorewebapp.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toModel(CreateCategoryRequestDto requestDto);
}
