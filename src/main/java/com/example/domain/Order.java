package com.example.domain;

import java.math.BigDecimal;

public class Order {

    private final Product product;

    private final BigDecimal price;

    private final boolean discount;

    public static Order createDiscount(Order order) {
        return new Order(order.getProduct(),  order.getPrice().multiply(new BigDecimal(-1)), true);
    }

    public Order(Product product, BigDecimal price) {
        this(product, price, false);
    }

    public Order(Product product, BigDecimal price, boolean discount) {
        this.product = product;
        this.price = price;
        this.discount = discount;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDiscount() {
        return discount;
    }
}
