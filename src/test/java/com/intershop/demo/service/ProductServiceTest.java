package com.intershop.demo.service;

import com.intershop.demo.entity.Product;
import com.intershop.demo.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product(1L, "Product 1", 10.0);

        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.save(product);

        assertNotNull(savedProduct);
        assertEquals(product, savedProduct);

        verify(productRepository, times(1)).save(product);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testFindAllProducts() {
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(new Product(1L, "Product 1", 10.0));
        expectedProducts.add(new Product(2L, "Product 2", 20.0));

        when(productRepository.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.findAll();

        assertEquals(expectedProducts.size(), actualProducts.size());
        assertEquals(expectedProducts.get(0), actualProducts.get(0));
        assertEquals(expectedProducts.get(1), actualProducts.get(1));

        verify(productRepository, times(1)).findAll();
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void testGetProductById() {
        long productId = 1L;
        Product expectedProduct = new Product(productId, "Product 1", 10.0);

        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(expectedProduct));

        Product actualProduct = productService.getProductById(productId);

        assertEquals(expectedProduct, actualProduct);

        verify(productRepository, times(1)).findById(productId);
        verifyNoMoreInteractions(productRepository);
    }

}