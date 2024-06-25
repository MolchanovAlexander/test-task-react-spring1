package com.example.bookstorewebapp.service.cartitem.impl;

import com.example.bookstorewebapp.exception.EntityNotFoundException;
import com.example.bookstorewebapp.model.CartItem;
import com.example.bookstorewebapp.repository.cartitem.CartItemRepository;
import com.example.bookstorewebapp.service.cartitem.CartItemService;
import java.util.Collection;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    @Override
    public void updateById(Long id, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no cart item with id: " + id)
        );
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteAll(Collection<CartItem> collection) {
        cartItemRepository.deleteAll(collection);
    }

    @Override
    public Set<CartItem> findAllByShoppingCartId(Long id) {
        return cartItemRepository.findAllByShoppingCartId(id);
    }

    @Override
    public void deleteByUserId(Long itemId) {
        if (!cartItemRepository.existsById(itemId)) {
            throw new EntityNotFoundException("There is no cart item with id:" + itemId);
        }
        cartItemRepository.deleteById(itemId);
    }
}
