package com.example.service.impl;

import com.example.repository.ProductRepository;
import com.example.domain.Order;
import com.example.domain.Product;
import com.example.domain.ProductType;
import com.example.domain.Receipt;
import com.example.service.CoffeeShopService;
import com.example.util.CoffeeShopUtil;

import java.math.BigDecimal;
import java.util.*;

import static com.example.domain.ProductType.*;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class CoffeeShopServiceImpl implements CoffeeShopService {

    private static final String WITH_DELIMITER = "with";

    private final ProductRepository productRepository;

    public CoffeeShopServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Receipt getReceipt(String[] orderRawInput) {
        final String rawString = String.join(" ", orderRawInput);
        final String[] rawOrders = rawString.split(",");
        final List<Product> products = parseInputAndGetProducts(rawOrders);
        final List<Order> orders = getOrders(products);
        final String currency = orders.stream().findFirst().get().getProduct().getCurrency();
        final List<Order> discounts = getDiscount(orders);
        if (discounts.size() > 0) {
            orders.addAll(discounts);
        }
        final BigDecimal total = orders.stream().map(Order::getPrice).reduce(ZERO, BigDecimal::add);

        return new Receipt(UUID.randomUUID().toString(), orders, total, currency, new Date());
    }

    private List<Order> getDiscount(List<Order> orders) {
        final List<Order> discounts = new ArrayList<>();
        final boolean isAnyExtraExists = orders.stream().anyMatch(order -> order.getProduct().getProductType() == EXTRA);
        final boolean isSnackExists = orders.stream().anyMatch(order -> order.getProduct().getProductType() == SNACK);
        final boolean isBeverage = orders.stream().anyMatch(order -> order.getProduct().getProductType() == COFFEE
                || order.getProduct().getProductType() == ProductType.JUICE);
        //case when snack and beverage then one of extra are free
        if (isSnackExists && isBeverage && isAnyExtraExists) {
            final Order extra = orders.stream().filter(order -> order.getProduct().getProductType() == EXTRA)
                    .findFirst().get();
            discounts.add(Order.createDiscount(extra));
        }
        final List<Order> beveragesOrders = orders.stream().filter(order -> order.getProduct().getProductType() == COFFEE
                || order.getProduct().getProductType() == JUICE).collect(toList());
        if (beveragesOrders.size() > 4) {
            if (beveragesOrders.size() % 5 > 1) {
                range(1, beveragesOrders.size() % 5).forEach(number -> {
                    discounts.add(Order.createDiscount(beveragesOrders.get((number * 5) - 1)));
                });
            } else {
                discounts.add(Order.createDiscount(beveragesOrders.get(4)));
            }
        }

        return discounts;
    }


    private List<Order> getOrders(List<Product> products) {
        return products.stream().map(product -> new Order(product, product.getPrice())).collect(toList());

    }

    @Override
    public String buildReceiptToPrint(Receipt receipt) {
        return CoffeeShopUtil.buildReceiptToPrint(receipt);
    }

    private List<Product> parseInputAndGetProducts(String[] selectedProducts) {
        final List<Product> products = new ArrayList<>();
        Arrays.stream(selectedProducts).forEach(rawOrderString -> {
            final String[] subOrders = rawOrderString.split(WITH_DELIMITER);
            Arrays.stream(subOrders).map(String::trim).forEach(orderString -> {
                productRepository.findProductByName(orderString).map(products::add).orElseThrow(() -> new RuntimeException(String.format("Can't find product %s", orderString)));
            });
        });

        return products;
    }


}
