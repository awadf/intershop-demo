package com.intershop.demo.controller;

import com.intershop.demo.dto.ShoppingCart;
import com.intershop.demo.entity.Product;
import com.intershop.demo.entity.ShoppingCartItem;
import com.intershop.demo.service.ProductService;
import com.intershop.demo.service.ShoppingCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {
    private final ShoppingCartItemService shoppingCartItemService;
    private final ProductService productService;

    @Autowired
    public ShoppingCartController(ShoppingCartItemService shoppingCartItemService, ProductService productService) {
        this.shoppingCartItemService = shoppingCartItemService;
        this.productService = productService;
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long productId, @RequestParam int quantity) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        shoppingCartItemService.addCartItem(cartItem);

        return ResponseEntity.ok("Product added to the cart");
    }

    @DeleteMapping("/remove-product/{itemId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long itemId) {
        ShoppingCartItem cartItem = shoppingCartItemService.getById(itemId);
        if (cartItem == null) {
            return ResponseEntity.notFound().build();
        }

        shoppingCartItemService.removeCartItem(cartItem);

        return ResponseEntity.ok("Product removed from the cart");
    }

    @PutMapping("/change-quantity/{itemId}")
    public ResponseEntity<?> changeItemQuantity(@PathVariable Long itemId, @RequestParam int quantity) {
        ShoppingCartItem cartItem = shoppingCartItemService.getById(itemId);
        if (cartItem == null) {
            return ResponseEntity.notFound().build();
        }

        cartItem.setQuantity(quantity);
        shoppingCartItemService.updateCartItem(cartItem);

        return ResponseEntity.ok("Cart item quantity changed");
    }

    @GetMapping("/display-cart")
    public ResponseEntity<ShoppingCart> displayCart() {
        List<ShoppingCartItem> cartItems = shoppingCartItemService.getCartItems();
        double totalPrice = calculateTotalPrice(cartItems);

        if (cartItems.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        ShoppingCart cart = new ShoppingCart(cartItems, totalPrice);
        return ResponseEntity.ok(cart);
    }

    private double calculateTotalPrice(List<ShoppingCartItem> cartItems) {
        double totalPrice = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        return totalPrice;
    }
}
