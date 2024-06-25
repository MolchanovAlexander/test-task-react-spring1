package com.example.bookstorewebapp.service.order;

import com.example.bookstorewebapp.dto.order.CreateOrderRequestDto;
import com.example.bookstorewebapp.dto.order.OrderResponseDto;
import com.example.bookstorewebapp.dto.order.UpdateStatusRequestDto;
import com.example.bookstorewebapp.dto.orderitem.OrderItemResponseDto;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    void placeOrder(Long userId, CreateOrderRequestDto requestDto);

    Set<OrderItemResponseDto> getOrderedItems(Long userId, Long orderId);

    OrderItemResponseDto getSpecificOrderedItem(Long userId, Long orderId, Long itemId);

    List<OrderResponseDto> getAllOrders(Long userId, Pageable pageable);

    void updateStatus(Long orderId, UpdateStatusRequestDto requestDto);
}
