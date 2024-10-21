package com.example.SpringBoot.service.cart;

import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.model.Cart;
import com.example.SpringBoot.repository.CartItemRepository;
import com.example.SpringBoot.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart =  cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cart not found!"));
        BigDecimal total = cart.getTotalAmount();
        cart.setTotalAmount(total);
        return cart;
    }

    @Override
    public void clearCart(Long id) {
        Cart cart =  cartRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cart not found!"));
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.delete(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }
}
