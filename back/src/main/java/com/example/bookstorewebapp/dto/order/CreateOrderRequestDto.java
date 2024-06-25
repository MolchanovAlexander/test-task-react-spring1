package com.example.bookstorewebapp.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOrderRequestDto {
    @NotBlank
    @Size(min = 6, max = 55)
    private String shippingAddress;
}
