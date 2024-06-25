package com.example.bookstorewebapp.controller;

import com.example.bookstorewebapp.dto.book.BookDto;
import com.example.bookstorewebapp.dto.book.BookSearchParameters;
import com.example.bookstorewebapp.dto.book.CreateBookRequestDto;
import com.example.bookstorewebapp.service.book.BookService;
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

@Tag(
        name = "Book management",
        description = "Endpoints for managing book"
)
@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @Operation(
            summary = "Create book",
            description = "create book entity from request body"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody CreateBookRequestDto requestDto) {
        return bookService.create(requestDto);
    }

    @Operation(
            summary = "Update book",
            description = "update book entity from request body"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public BookDto updateBook(
            @PathVariable Long id,
            @Valid @RequestBody CreateBookRequestDto requestDto
    ) {
        return bookService.updateById(id, requestDto);
    }

    @Operation(
            summary = "Get all books",
            description = "Get all books"
    )
    @GetMapping
    public List<BookDto> getAll(
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        return bookService.findAll(pageable);
    }

    @Operation(
            summary = "Get book by id",
            description = "Get book by id from url /id"
    )
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @Operation(
            summary = "Search book",
            description = "Search book by author and/or title"
    )
    @GetMapping("/search")
    public List<BookDto> search(
            BookSearchParameters searchParameters,
            @ParameterObject @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        return bookService.search(searchParameters, pageable);
    }

    @Operation(
            summary = "Delete book by id",
            description = "Delete book by id from url /id"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
