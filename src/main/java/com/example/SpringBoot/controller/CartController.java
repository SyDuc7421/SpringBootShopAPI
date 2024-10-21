package com.example.SpringBoot.controller;

import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.model.Cart;
import com.example.SpringBoot.response.APIResponse;
import com.example.SpringBoot.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<APIResponse> getCartById(@PathVariable Long cartId){
        try{
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(new APIResponse("Success to get Cart by Id!", cart));
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<APIResponse> clearCart(@PathVariable Long cartId){
        try{
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new APIResponse("Success to clear the Cart!", null));
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{cartId}/totalAmount")
    public ResponseEntity<APIResponse> getTotalAmount(@PathVariable Long cartId){
        try{
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new APIResponse("Success to get Total Amount!", cart.getTotalAmount()));
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
