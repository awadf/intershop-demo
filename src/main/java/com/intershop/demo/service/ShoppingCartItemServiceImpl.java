package com.intershop.demo.service;

import com.intershop.demo.entity.ShoppingCartItem;
import com.intershop.demo.repository.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    private final ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    public ShoppingCartItemServiceImpl(ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    @Override
    public void addCartItem(ShoppingCartItem cartItem) { shoppingCartItemRepository.save(cartItem); }

    @Override
    public ShoppingCartItem getById(Long cartItemId) { return shoppingCartItemRepository.findById(cartItemId).orElse(null); }

    @Override
    public void removeCartItem(ShoppingCartItem cartItem) {
        shoppingCartItemRepository.delete(cartItem);
    }

    @Override
    public void updateCartItem(ShoppingCartItem cartItem) {
        shoppingCartItemRepository.save(cartItem);
    }

    @Override
    public List<ShoppingCartItem> getCartItems() {
        return shoppingCartItemRepository.findAllWithChildren();
    }

}
