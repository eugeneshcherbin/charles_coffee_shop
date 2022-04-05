package com.example.domain;

import java.math.BigDecimal;

public class Product {

    public static final String DEFAULT_CURRENCY = "CHF";

    private final Long id;

    private final String name;

    private final BigDecimal price;

    private final ProductType productType;

    private final String currency;

    public Product(Long id, String name, BigDecimal price, ProductType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productType = type;
        this.currency = DEFAULT_CURRENCY;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getCurrency() {
        return currency;
    }
}
