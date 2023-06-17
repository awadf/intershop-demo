package com.intershop.demo.service;

import com.intershop.demo.entity.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartItemService {

    void addCartItem(ShoppingCartItem cartItem);

    ShoppingCartItem getById(Long cartItemId);

    void removeCartItem(ShoppingCartItem cartItem);

    void updateCartItem(ShoppingCartItem cartItem);

    List<ShoppingCartItem> getCartItems();
}