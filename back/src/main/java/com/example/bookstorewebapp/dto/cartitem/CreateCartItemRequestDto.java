package com.example.bookstorewebapp.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @NotNull
    @Min(0)
    private Long bookId;
    @NotNull
    @Min(1)
    private Integer quantity;
}
