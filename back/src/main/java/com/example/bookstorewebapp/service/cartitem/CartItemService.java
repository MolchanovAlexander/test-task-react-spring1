package com.example.bookstorewebapp.service.cartitem;

import com.example.bookstorewebapp.model.CartItem;
import java.util.Collection;
import java.util.Set;

public interface CartItemService {
    Set<CartItem> findAllByShoppingCartId(Long id);

    void save(CartItem cartItem);

    void deleteByUserId(Long itemId);

    void updateById(Long id, Integer quantity);

    void deleteAll(Collection<CartItem> collection);
}
