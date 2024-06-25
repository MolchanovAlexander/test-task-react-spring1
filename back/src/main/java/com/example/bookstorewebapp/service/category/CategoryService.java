package com.example.bookstorewebapp.service.category;

import com.example.bookstorewebapp.dto.category.CategoryResponseDto;
import com.example.bookstorewebapp.dto.category.CreateCategoryRequestDto;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryResponseDto> findAll(Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryResponseDto save(CreateCategoryRequestDto requestDto);

    CategoryResponseDto updateById(Long id, CreateCategoryRequestDto requestDto);

    void deleteById(Long id);

    void isAllCategoriesPresent(Set<Long> categoryIds);

    void isEntityExist(Long id);
}
