package com.example.bookstorewebapp.controller;

import com.example.bookstorewebapp.dto.cartitem.CreateCartItemRequestDto;
import com.example.bookstorewebapp.dto.cartitem.UpdateCartItemRequestDto;
import com.example.bookstorewebapp.dto.shoppingcart.ShoppingCartResponseDto;
import com.example.bookstorewebapp.model.User;
import com.example.bookstorewebapp.service.shoppingcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "ShoppingCart management",
        description = "Endpoints for managing ShoppingCart"
)
@RestController
@RequestMapping(value = "/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(
            summary = "Add item to your ShoppingCart.",
            description = "Add item to your ShoppingCart."
    )
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addItemToShoppingCart(
            @Valid @RequestBody CreateCartItemRequestDto requestDto,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.save(user, requestDto);
    }

    @Operation(
            summary = "Update ShoppingCart item.",
            description = "update ShoppingCart item by id."
    )
    @PutMapping("/cart-items/{itemId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateShoppingCartItem(
            @Valid @RequestBody UpdateCartItemRequestDto requestDto,
            @PathVariable Long itemId
    ) {
        shoppingCartService.update(itemId, requestDto);
    }

    @Operation(
            summary = "Get ShoppingCart.",
            description = "get your ShoppingCart entity."
    )
    @GetMapping
    public ShoppingCartResponseDto getShoppingCart(
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.getByUserId(user.getId());
    }

    @Operation(
            summary = "Delete ShoppingCart",
            description = "Delete ShoppingCart entity "
    )
    @DeleteMapping("/cart-items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(
            @PathVariable Long itemId
    ) {
        shoppingCartService.deleteCartItemById(itemId);
    }
}
