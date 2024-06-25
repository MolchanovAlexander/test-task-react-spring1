package com.example.bookstorewebapp.mapper;

import com.example.bookstorewebapp.config.MapperConfig;
import com.example.bookstorewebapp.dto.order.CreateOrderRequestDto;
import com.example.bookstorewebapp.dto.order.OrderResponseDto;
import com.example.bookstorewebapp.model.Order;
import com.example.bookstorewebapp.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    Order toModel(CreateOrderRequestDto requestDto, ShoppingCart responseDto);
}
