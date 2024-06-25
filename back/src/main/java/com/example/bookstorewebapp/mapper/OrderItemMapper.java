package com.example.bookstorewebapp.mapper;

import com.example.bookstorewebapp.config.MapperConfig;
import com.example.bookstorewebapp.dto.orderitem.OrderItemResponseDto;
import com.example.bookstorewebapp.model.CartItem;
import com.example.bookstorewebapp.model.Order;
import com.example.bookstorewebapp.model.OrderItem;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = OrderMapper.class)
public interface OrderItemMapper {
    @Mapping(target = "order", source = "order")
    @Mapping(target = "price", source = "cartItem", qualifiedByName = "priceCalc")
    @Mapping(target = "id", ignore = true)
    OrderItem toModel(CartItem cartItem, Order order);

    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);

    @Named("priceCalc")
    default BigDecimal priceCalc(CartItem cartItem) {
        return cartItem.getBook().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
    }
}
