package com.example.SpringBoot.controller;

import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.response.APIResponse;
import com.example.SpringBoot.service.cart.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;

    @PostMapping
    public ResponseEntity<APIResponse> addCartItem(@RequestParam Long cartId,
                                                   @RequestParam Long productId,
                                                   @RequestParam int quantity) {
        try {

            cartItemService.addItemToCart(cartId, productId, quantity);
            return  ResponseEntity.ok(new APIResponse("Success to add increase cart item quantity", null));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping
    public ResponseEntity<APIResponse> removeCartItem(@RequestParam Long cartId,
                                                      @RequestParam Long productId) {
        try{
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new APIResponse("Success to remove cart item quantity", null));
        }

        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping
    public  ResponseEntity<APIResponse> updateCartItemQuantity(@RequestParam Long cartId,
                                                               @RequestParam Long productId,
                                                               @RequestParam int quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new APIResponse("Success to update cart item quantity", null));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
