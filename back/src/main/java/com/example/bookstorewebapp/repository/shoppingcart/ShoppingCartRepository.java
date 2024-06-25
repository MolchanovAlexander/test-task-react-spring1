package com.example.bookstorewebapp.repository.shoppingcart;

import com.example.bookstorewebapp.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @EntityGraph(attributePaths = {"cartItems.book", "user"})
    Optional<ShoppingCart> findShoppingCartByUserId(Long id);
}
