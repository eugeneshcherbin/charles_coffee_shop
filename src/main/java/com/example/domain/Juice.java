package com.example.domain;

import java.math.BigDecimal;

public class Juice  extends Product {

    private final Double capacity;

    private final String measureUnit = "l";

    public Juice(Long id, String name, BigDecimal price, Double capacity) {
        super(id, name, price, ProductType.JUICE);
        this.capacity = capacity;
    }
}
