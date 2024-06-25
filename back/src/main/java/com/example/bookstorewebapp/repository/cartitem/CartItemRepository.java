package com.example.bookstorewebapp.repository.cartitem;

import com.example.bookstorewebapp.model.CartItem;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartItemRepository extends JpaRepository<CartItem, Long>,
        JpaSpecificationExecutor<CartItem> {
    Set<CartItem> findAllByShoppingCartId(Long id);

    void deleteById(Long id);
}
