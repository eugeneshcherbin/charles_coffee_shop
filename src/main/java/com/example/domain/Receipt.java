package com.example.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Receipt {

    private final String id;

    private final List<Order> orders;

    private final BigDecimal totalAmount;

    private final String currency;

    private final Date receiptDate;

    public Receipt(String id, List<Order> orders, BigDecimal totalAmount, String currency, Date receiptDate) {
        this.id = id;
        this.orders = orders;
        this.totalAmount = totalAmount;
        this.receiptDate = receiptDate;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Date getReceiptDate() {
        return new Date(receiptDate.getTime());
    }

    public String getCurrency() {
        return currency;
    }
}
