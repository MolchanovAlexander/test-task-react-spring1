package com.example.bookstorewebapp.mapper;

import com.example.bookstorewebapp.config.MapperConfig;
import com.example.bookstorewebapp.dto.cartitem.CartItemResponseDto;
import com.example.bookstorewebapp.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstorewebapp.model.Book;
import com.example.bookstorewebapp.model.CartItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    @Mapping(target = "bookTitle", source = "book", qualifiedByName = "getTitleByBook")
    @Mapping(target = "bookId", source = "book", qualifiedByName = "bookIdByBook")
    CartItemResponseDto toDto(CartItem cartItem);

    @Mapping(target = "book", ignore = true)
    CartItem toModel(CreateCartItemRequestDto requestDto);

    @AfterMapping
    default void setBook(CreateCartItemRequestDto requestDto, @MappingTarget CartItem cartItem) {
        Book book = new Book();
        book.setId(requestDto.getBookId());
        cartItem.setBook(book);
    }

    @Named(value = "bookIdByBook")
    default Long bookIdByBook(Book book) {
        return book.getId();
    }

    @Named(value = "getTitleByBook")
    default String getTitleByBook(Book book) {
        return book.getTitle();
    }
}
