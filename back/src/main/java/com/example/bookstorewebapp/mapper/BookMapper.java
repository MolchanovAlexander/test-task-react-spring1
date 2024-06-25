package com.example.bookstorewebapp.mapper;

import com.example.bookstorewebapp.config.MapperConfig;
import com.example.bookstorewebapp.dto.book.BookDto;
import com.example.bookstorewebapp.dto.book.BookDtoWithoutCategories;
import com.example.bookstorewebapp.dto.book.CreateBookRequestDto;
import com.example.bookstorewebapp.model.Book;
import com.example.bookstorewebapp.model.Category;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    @Mapping(target = "categoryIds", ignore = true)
    @Mapping(target = "author", source = "author_name")
    BookDto toDto(Book book);

    BookDtoWithoutCategories toDtoWithoutCategories(Book book);

    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "author_name", source = "author")
    Book toModel(CreateBookRequestDto requestDto);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(categoryIds);
    }

    @AfterMapping
    default void setCategories(CreateBookRequestDto requestDto, @MappingTarget Book book) {
        Set<Category> categories = requestDto.getCategoryIds().stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }
}
