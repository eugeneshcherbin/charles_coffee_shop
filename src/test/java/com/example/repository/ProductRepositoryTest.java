package com.example.repository;

import com.example.repository.impl.ProductRepositoryImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductRepositoryTest {

    private final ProductRepository productRepository = new ProductRepositoryImpl();

    @Test
    public void itShouldFindProductByName() {
        assertTrue(productRepository.findProductByName("small coffee").isPresent(),
                "Should find small coffee this product should be exists");
        assertTrue(productRepository.findProductByName("medium coffee").isPresent(),
                "Should find medium coffee this product should be exists");
        assertFalse(productRepository.findProductByName("foo coffee").isPresent(),
                "foo coffee is not exists product");
    }
}
