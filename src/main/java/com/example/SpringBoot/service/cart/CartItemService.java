package com.example.SpringBoot.service.cart;

import com.example.SpringBoot.exception.ResourceNotFoundException;
import com.example.SpringBoot.model.Cart;
import com.example.SpringBoot.model.CartItem;
import com.example.SpringBoot.model.Product;
import com.example.SpringBoot.repository.CartItemRepository;
import com.example.SpringBoot.repository.CartRepository;
import com.example.SpringBoot.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. Get the cart
        Cart cart = cartService.getCart(cartId);
        // 2. Get the product
        Product product = productService.getProductById(productId);
        // 3. Check if the product already exists in cart.

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item->item.getProduct().getId().equals(product.getId()))
                .findFirst().orElse(new CartItem());
        // 4. If no create new cart Item.
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setTotalPrice();

        }
        else{
            // 5. If yes increase cart item quantity.
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice();
            cart.addCartItem(cartItem);
        }

        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemoved = getCartItem(cartId, productId);
        cart.removeCartItem(itemToRemoved);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
    }

    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart
                .getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId()
                        .equals(productId)).findFirst().orElseThrow(()-> new ResourceNotFoundException("Cart item not found!"));
    }
}
