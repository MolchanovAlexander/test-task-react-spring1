package com.example.bookstorewebapp.service.category.impl;

import com.example.bookstorewebapp.dto.category.CategoryResponseDto;
import com.example.bookstorewebapp.dto.category.CreateCategoryRequestDto;
import com.example.bookstorewebapp.exception.EntityNotFoundException;
import com.example.bookstorewebapp.mapper.CategoryMapper;
import com.example.bookstorewebapp.model.Category;
import com.example.bookstorewebapp.repository.category.CategoryRepository;
import com.example.bookstorewebapp.service.category.CategoryService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryMapper.toModel(requestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto updateById(Long id, CreateCategoryRequestDto requestDto) {
        isEntityExist(id);
        Category category = categoryMapper.toModel(requestDto);
        category.setId(id);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id: " + id)
        );
        return categoryMapper.toDto(category);
    }

    @Override
    public void deleteById(Long id) {
        isEntityExist(id);
        categoryRepository.deleteById(id);
    }

    public void isAllCategoriesPresent(Set<Long> categoryIds) {
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new EntityNotFoundException(
                    "Some of these categories are absent: " + categoryIds);
        }
    }

    public void isEntityExist(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "There is no category with id: " + id
            );
        }
    }
}
