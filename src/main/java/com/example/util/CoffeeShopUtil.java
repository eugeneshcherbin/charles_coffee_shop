package com.example.util;

import com.example.domain.Order;
import com.example.domain.ProductType;
import com.example.domain.Receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CoffeeShopUtil {

    public static final int THRESHOLD_LENGTH = 28;

    private static final String HEADER_TEMPLATE = "  %s\t\t\t\t\t\t\t\t\t%s \t  %s %s";

    private static final String ITEM_TEMPLATE = "  %s\t\t\t%s \t\t  %s %s";

    private CoffeeShopUtil() {
        //
    }

    public static String buildReceiptToPrint(Receipt receipt) {
        final StringBuilder sb = new StringBuilder();

        sb.append("---------------------------------------------------------------------");
        sb.append(System.lineSeparator());
        sb.append("\t\t\t\t\t\t\t\tRECEIPT      ");
        sb.append(System.lineSeparator());
        sb.append(" Receipt Date: ").append(receipt.getReceiptDate());

        sb.append(System.lineSeparator());
        sb.append("----------------------------------------------------------------------");
        sb.append(System.lineSeparator());

        sb.append("======================================================================");
        sb.append(System.lineSeparator());
        sb.append(String.format(HEADER_TEMPLATE, "Item", "Qty", "Price", " "));
        sb.append(System.lineSeparator());
        final boolean isDiscounts = receipt.getOrders().stream().anyMatch(order -> order.isDiscount());
        final Map<ProductType, List<Order>> groupedByProductType = receipt.getOrders().stream()
                .collect(Collectors.groupingBy((item) -> item.getProduct().getProductType()));
        Arrays.stream(ProductType.values()).forEach(productType -> {
            if (groupedByProductType.containsKey(productType)) {
                printGroup(receipt, sb, groupedByProductType, productType);

            }
        });

        if (isDiscounts) {
            printDiscounts(sb, receipt.getOrders().stream().filter(Order::isDiscount).collect(Collectors.toList()),
                    receipt);

        }
        sb.append("---------------------------------------------------------------------");
        sb.append(System.lineSeparator());
        sb.append(" Total Amount : ").append(CoffeeShopUtil.round(receipt.getTotalAmount()))
                .append(" " + receipt.getCurrency());
        return sb.toString();
    }

    private static void printDiscounts(StringBuilder sb, List<Order> discountItems, Receipt receipt) {
        sb.append(System.lineSeparator());
        sb.append("---------------------------------------------------------------------");
        sb.append(System.lineSeparator());
        sb.append(" Discounts ");
        sb.append(System.lineSeparator());
        sb.append(System.lineSeparator());
        discountItems.forEach(item->{
            sb.append(String.format(ITEM_TEMPLATE, tapName(item.getProduct().getName()),
                    1, round(item.getPrice()), receipt.getCurrency()));
            sb.append(System.lineSeparator());
        });
        sb.append(System.lineSeparator());
    }

    private static void printGroup(Receipt receipt, StringBuilder sb,
                                   Map<ProductType, List<Order>> groupedByProductType, ProductType productType) {
        final Map<String, List<Order>> groupedByName = groupedByProductType.get(productType)
                .stream().filter(item->!item.isDiscount())
                .collect(Collectors.groupingBy((item) -> item.getProduct().getName()));
        groupedByName.forEach((itemName, items) -> {
            final String name = tapName(itemName);
            final int qty = items.size();
            final BigDecimal price = items.stream().filter(order->!order.isDiscount()).map(Order::getPrice)
                    .reduce(BigDecimal.ZERO,
                    BigDecimal::add);
            sb.append(System.lineSeparator());
            sb.append(String.format(ITEM_TEMPLATE, name, qty, round(price), receipt.getCurrency()));
        });
    }

    public static String tapName(String itemName) {
        String name = itemName;
        if (name.length() < THRESHOLD_LENGTH) {
            name = name + " ".repeat(THRESHOLD_LENGTH - name.length());
        }
        return name;
    }

    public static BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }


}
