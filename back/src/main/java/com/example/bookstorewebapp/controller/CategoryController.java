package com.example.bookstorewebapp.controller;

import com.example.bookstorewebapp.dto.book.BookDtoWithoutCategories;
import com.example.bookstorewebapp.dto.category.CategoryResponseDto;
import com.example.bookstorewebapp.dto.category.CreateCategoryRequestDto;
import com.example.bookstorewebapp.service.book.BookService;
import com.example.bookstorewebapp.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category management", description = "Endpoints for managing category")
@RestController
@RequestMapping(value = "/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @Operation(
            summary = "Create category",
            description = "create category entity from request body"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryResponseDto createCategory(
            @Valid @RequestBody CreateCategoryRequestDto requestDto
    ) {
        return categoryService.save(requestDto);
    }

    @Operation(
            summary = "Update category",
            description = "update category entity from request body"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public CategoryResponseDto updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CreateCategoryRequestDto requestDto
    ) {
        return categoryService.updateById(id, requestDto);
    }

    @Operation(
            summary = "Get all categories",
            description = "Get all categories"
    )
    @GetMapping
    public List<CategoryResponseDto> getAll(
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        return categoryService.findAll(pageable);
    }

    @Operation(
            summary = "Get all books by category",
            description = "Get all books by category"
    )

    @GetMapping("/{id}/books")
    public List<BookDtoWithoutCategories> findAllByCategoryId(
            @PathVariable Long id, Pageable pageable
    ) {
        return bookService.findAllByCategoryId(id, pageable);
    }

    @Operation(
            summary = "Get category",
            description = "Get category by id from url /id"
    )
    @GetMapping("/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @Operation(
            summary = "Delete category by id",
            description = "Delete category by id from url /id"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}
