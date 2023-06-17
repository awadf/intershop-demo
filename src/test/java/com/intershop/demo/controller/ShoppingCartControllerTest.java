package com.intershop.demo.controller;

import com.intershop.demo.entity.Product;
import com.intershop.demo.entity.ShoppingCartItem;
import com.intershop.demo.service.ProductServiceImpl;
import com.intershop.demo.service.ShoppingCartItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ShoppingCartController.class)
public class ShoppingCartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartItemServiceImpl shoppingCartItemService;

    @MockBean
    private ProductServiceImpl productService;

    @Test
    public void testAddProductToCart() throws Exception {
        // Mock the product repository
        final Product product = new Product(1L, "Test Product", 9.99);

        when(productService.getProductById(1L)).thenReturn(product);

        // Perform the POST request
        mockMvc.perform(post("/api/shopping-cart/add-product/1")
                .param("quantity", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added to the cart"));
    }

    @Test
    public void testRemoveProductFromCart() throws Exception {
        // Mock the existence of the shopping cart item
        when(shoppingCartItemService.getById(1L)).thenReturn(new ShoppingCartItem());

        // Perform the DELETE request
        mockMvc.perform(delete("/api/shopping-cart/remove-product/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product removed from the cart"));
    }

    @Test
    public void testRemoveProductFromCart_ItemNotFound() throws Exception {
        // Mock the absence of the shopping cart item
        when(shoppingCartItemService.getById(1L)).thenReturn(null);

        // Perform the DELETE request
        mockMvc.perform(delete("/api/shopping-cart/remove-product/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testChangeItemQuantity() throws Exception {
        // Mock the existence of the shopping cart item
        when(shoppingCartItemService.getById(1L)).thenReturn(new ShoppingCartItem());

        // Perform the PUT request
        mockMvc.perform(put("/api/shopping-cart/change-quantity/1")
                .param("quantity", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Cart item quantity changed"));
    }

    @Test
    public void testChangeItemQuantity_ItemNotFound() throws Exception {
        // Mock the absence of the shopping cart item
        when(shoppingCartItemService.getById(1L)).thenReturn(null);

        // Perform the PUT request
        mockMvc.perform(put("/api/shopping-cart/change-quantity/1")
                .param("quantity", "5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDisplayShoppingCart() throws Exception {
        // Create a sample shopping cart item
        final Product testProduct = new Product(1L, "Test Product", 9.99);
        final ShoppingCartItem item = new ShoppingCartItem(1L, testProduct, 2);

        // Mock the shopping cart items retrieval
        when(shoppingCartItemService.getCartItems()).thenReturn(List.of(item));

        // Perform the GET request
        mockMvc.perform(get("/api/shopping-cart/display-cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItems[0].id").value(1))
                .andExpect(jsonPath("$.cartItems[0].quantity").value(2))
                .andExpect(jsonPath("$.cartItems[0].product.id").value(1))
                .andExpect(jsonPath("$.cartItems[0].product.name").value("Test Product"))
                .andExpect(jsonPath("$.cartItems[0].product.price").value(9.99))
                .andExpect(jsonPath("$.totalPrice").value(19.98));
    }
}
