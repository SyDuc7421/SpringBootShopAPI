package com.example.SpringBoot.repository;

import com.example.SpringBoot.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    public void deleteAllByCartId(Long cartId);
}
