package com.example.repository.impl;

import com.example.domain.Juice;
import com.example.domain.Product;
import com.example.domain.ProductType;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class DataBase {
    private Set<Product> availableProducts = new HashSet<>();

    {
        availableProducts.add(new Product(Long.valueOf(1), "small coffee", new BigDecimal(2.5), ProductType.COFFEE));
        availableProducts.add(new Product(Long.valueOf(2), "medium coffee", new BigDecimal(3.0), ProductType.COFFEE));
        availableProducts.add(new Product(Long.valueOf(3), "large coffee", new BigDecimal(3.5), ProductType.COFFEE));

        availableProducts.add(new Product(Long.valueOf(4), "Bacon Roll", new BigDecimal(4.5), ProductType.SNACK));

        availableProducts.add(new Juice(Long.valueOf(5), "Freshly squeezed orange juice", new BigDecimal(3.95), 0.25));

        availableProducts.add(new Product(Long.valueOf(6), "Extra milk", new BigDecimal(0.3), ProductType.EXTRA));
        availableProducts.add(new Product(Long.valueOf(7), "Foamed milk", new BigDecimal(0.5), ProductType.EXTRA));
        availableProducts.add(new Product(Long.valueOf(8), "Special roast coffee", new BigDecimal(0.9),
                ProductType.EXTRA));
    }

    public Set<Product> getAvailableProducts() {
        return Collections.unmodifiableSet(availableProducts);
    }
}
