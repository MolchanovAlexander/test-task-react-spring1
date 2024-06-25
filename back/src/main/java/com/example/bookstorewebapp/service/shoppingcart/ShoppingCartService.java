package com.example.bookstorewebapp.service.shoppingcart;

import com.example.bookstorewebapp.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstorewebapp.dto.cartitem.UpdateCartItemRequestDto;
import com.example.bookstorewebapp.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.bookstorewebapp.model.ShoppingCart;
import com.example.bookstorewebapp.model.User;

public interface ShoppingCartService {
    ShoppingCart createShoppingCart(User user);

    void deleteCartItemById(Long itemId);

    ShoppingCartResponseDto getByUserId(Long id);

    ShoppingCart getSoppingCartByUserId(Long id);

    void save(User user, CreateCartItemRequestDto requestDto);

    void update(Long id, UpdateCartItemRequestDto requestDto);
}
