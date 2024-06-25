package com.example.bookstorewebapp.service.book.impl;

import com.example.bookstorewebapp.dto.book.BookDto;
import com.example.bookstorewebapp.dto.book.BookDtoWithoutCategories;
import com.example.bookstorewebapp.dto.book.BookSearchParameters;
import com.example.bookstorewebapp.dto.book.CreateBookRequestDto;
import com.example.bookstorewebapp.exception.EntityNotFoundException;
import com.example.bookstorewebapp.mapper.BookMapper;
import com.example.bookstorewebapp.model.Book;
import com.example.bookstorewebapp.repository.book.BookRepository;
import com.example.bookstorewebapp.repository.book.BookSpecificationBuilder;
import com.example.bookstorewebapp.service.book.BookService;
import com.example.bookstorewebapp.service.category.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private static final String MESSAGE_BOOK_NOT_EXIST = "book";
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder specificationBuilder;

    @Override
    public List<BookDtoWithoutCategories> findAllByCategoryId(Long id, Pageable pageable) {
        categoryService.isEntityExist(id);
        return bookRepository.findAllByCategoryId(id, pageable).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    public BookDto create(CreateBookRequestDto requestDto) {
        categoryService.isAllCategoriesPresent(requestDto.getCategoryIds());
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public BookDto updateById(Long id, CreateBookRequestDto requestDto) {
        isExist(id);
        Book book = bookMapper.toModel(requestDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> search(BookSearchParameters params, Pageable pageable) {
        Specification<Book> bookSpecification =
                specificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        isExist(id);
        bookRepository.deleteById(id);
    }

    public void isExist(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "There is no " + MESSAGE_BOOK_NOT_EXIST + " with id: " + id
            );
        }
    }
}
