package com.intershop.demo.service;
// ShoppingCartItemServiceTest.java

import com.intershop.demo.entity.ShoppingCartItem;
import com.intershop.demo.repository.ShoppingCartItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShoppingCartItemServiceTest {

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @InjectMocks
    private ShoppingCartItemServiceImpl shoppingCartItemService;

    public ShoppingCartItemServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCartItem() {
        ShoppingCartItem cartItem = new ShoppingCartItem();

        when(shoppingCartItemRepository.save(cartItem)).thenReturn(cartItem);

        shoppingCartItemService.addCartItem(cartItem);

        verify(shoppingCartItemRepository, times(1)).save(cartItem);
        verifyNoMoreInteractions(shoppingCartItemRepository);
    }

    @Test
    public void testGetById() {
        long cartItemId = 1L;
        ShoppingCartItem cartItem = new ShoppingCartItem();

        when(shoppingCartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItem));

        ShoppingCartItem retrievedItem = shoppingCartItemService.getById(cartItemId);

        assertNotNull(retrievedItem);
        assertEquals(cartItem, retrievedItem);

        verify(shoppingCartItemRepository, times(1)).findById(cartItemId);
        verifyNoMoreInteractions(shoppingCartItemRepository);
    }

    @Test
    public void testRemoveCartItem() {
        ShoppingCartItem cartItem = new ShoppingCartItem();

        shoppingCartItemService.removeCartItem(cartItem);

        verify(shoppingCartItemRepository, times(1)).delete(cartItem);
        verifyNoMoreInteractions(shoppingCartItemRepository);
    }

    @Test
    public void testUpdateCartItem() {
        ShoppingCartItem cartItem = new ShoppingCartItem();

        when(shoppingCartItemRepository.save(cartItem)).thenReturn(cartItem);

        shoppingCartItemService.updateCartItem(cartItem);

        verify(shoppingCartItemRepository, times(1)).save(cartItem);
        verifyNoMoreInteractions(shoppingCartItemRepository);
    }

    @Test
    public void testGetCartItems() {
        List<ShoppingCartItem> expectedItems = new ArrayList<>();
        expectedItems.add(new ShoppingCartItem());
        expectedItems.add(new ShoppingCartItem());

        when(shoppingCartItemRepository.findAllWithChildren()).thenReturn(expectedItems);

        List<ShoppingCartItem> actualItems = shoppingCartItemService.getCartItems();

        assertEquals(expectedItems.size(), actualItems.size());
        assertEquals(expectedItems.get(0), actualItems.get(0));
        assertEquals(expectedItems.get(1), actualItems.get(1));

        verify(shoppingCartItemRepository, times(1)).findAllWithChildren();
        verifyNoMoreInteractions(shoppingCartItemRepository);
    }
}
