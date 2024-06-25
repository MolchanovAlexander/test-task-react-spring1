package com.example.bookstorewebapp.repository.order;

import com.example.bookstorewebapp.model.Order;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems "
            + "WHERE o.user.id = :id AND o.id = :orderId")
    Optional<Order> findOrderByUserIdAndOrderId(Long id, Long orderId);

    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems "
            + "WHERE o.user.id = :id")
    Page<Order> findOrderByUserId(Long id, @NonNull Pageable pageable);

    @NonNull
    @EntityGraph(attributePaths = {"orderItems.book", "user"})
    Page<Order> findAll(@NonNull Pageable pageable);
}
