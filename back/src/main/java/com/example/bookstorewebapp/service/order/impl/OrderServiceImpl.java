package com.example.bookstorewebapp.service.order.impl;

import com.example.bookstorewebapp.dto.order.CreateOrderRequestDto;
import com.example.bookstorewebapp.dto.order.OrderResponseDto;
import com.example.bookstorewebapp.dto.order.UpdateStatusRequestDto;
import com.example.bookstorewebapp.dto.orderitem.OrderItemResponseDto;
import com.example.bookstorewebapp.exception.EntityNotFoundException;
import com.example.bookstorewebapp.mapper.OrderItemMapper;
import com.example.bookstorewebapp.mapper.OrderMapper;
import com.example.bookstorewebapp.model.CartItem;
import com.example.bookstorewebapp.model.Order;
import com.example.bookstorewebapp.model.OrderItem;
import com.example.bookstorewebapp.model.Role;
import com.example.bookstorewebapp.model.ShoppingCart;
import com.example.bookstorewebapp.model.Status;
import com.example.bookstorewebapp.model.User;
import com.example.bookstorewebapp.repository.order.OrderRepository;
import com.example.bookstorewebapp.repository.orderitem.OrderItemRepository;
import com.example.bookstorewebapp.service.authentication.AuthenticationService;
import com.example.bookstorewebapp.service.cartitem.CartItemService;
import com.example.bookstorewebapp.service.order.OrderService;
import com.example.bookstorewebapp.service.shoppingcart.ShoppingCartService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final OrderMapper orderMapper;
    private final CartItemService cartItemService;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    @Override
    public void placeOrder(Long userId, CreateOrderRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartService.getSoppingCartByUserId(userId);
        User user = authenticationService.findById(userId);
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        BigDecimal total = getTotal(cartItems);
        Order newOrder = fillOrder(requestDto, shoppingCart, user, total);
        orderRepository.save(newOrder);
        orderItemRepository.saveAll(getOrderItems(cartItems, newOrder));
        cartItemService.deleteAll(cartItems);
    }

    @Override
    public Set<OrderItemResponseDto> getOrderedItems(
            Long userId, Long orderId
    ) {
        Order order = orderRepository.findOrderByUserIdAndOrderId(userId, orderId).orElseThrow(
                () -> new EntityNotFoundException(
                        "There is no order with userId:" + userId + " orderId:" + orderId)
        );
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemResponseDto getSpecificOrderedItem(Long userId, Long orderId, Long itemId) {
        Order order = orderRepository.findOrderByUserIdAndOrderId(userId, orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no order with userId:" + userId + " orderId:" + orderId)
                );
        return order.getOrderItems().stream()
                .filter(o -> o.getId().equals(itemId))
                .map(orderItemMapper::toDto)
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "There is no order item with id:" + itemId)
                );
    }

    @Override
    public List<OrderResponseDto> getAllOrders(Long userId, Pageable pageable) {
        if (authenticationService.findById(userId).getRoles().stream()
                .map(r -> r.getRole())
                .toList()
                .contains(Role.RoleName.ADMIN)) {
            return orderRepository.findAll(pageable).stream()
                    .map(orderMapper::toDto)
                    .toList();
        } else {
            return orderRepository.findOrderByUserId(userId, pageable).stream()
                    .map(orderMapper::toDto)
                    .toList();
        }

    }

    @Override
    public void updateStatus(Long orderId, UpdateStatusRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no order with orderId:" + orderId)
                );
        order.setStatus(Status.valueOf(requestDto.getStatus()));
        orderRepository.save(order);
    }

    private Order fillOrder(
            CreateOrderRequestDto requestDto,
            ShoppingCart shoppingCart,
            User user,
            BigDecimal total
    ) {
        Order newOrder = orderMapper.toModel(requestDto, shoppingCart);
        newOrder.setUser(user);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatus(Status.PENDING);
        newOrder.setTotal(total);
        return newOrder;
    }

    private BigDecimal getTotal(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(c -> c.getBook().getPrice().multiply(BigDecimal.valueOf(c.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderItem> getOrderItems(Set<CartItem> cartItems, Order newOrder) {
        return cartItems.stream()
                .map(c -> orderItemMapper.toModel(c, newOrder))
                .collect(Collectors.toSet());
    }
}
