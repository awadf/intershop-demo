package com.intershop.demo.repository;

import com.intershop.demo.entity.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    @Query("from ShoppingCartItem cart left join fetch cart.product product")
    List<ShoppingCartItem> findAllWithChildren();
}
