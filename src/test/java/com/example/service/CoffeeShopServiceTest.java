package com.example.service;

import com.example.domain.Receipt;
import com.example.repository.impl.ProductRepositoryImpl;
import com.example.service.impl.CoffeeShopServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.example.util.CoffeeShopUtil.round;
import static org.junit.jupiter.api.Assertions.*;

public class CoffeeShopServiceTest {

    private final CoffeeShopService coffeeShopService = new CoffeeShopServiceImpl(new ProductRepositoryImpl());


    @Test
    public void testItShouldReturnReceiptWitOneExtraFreeDiscount() {

        final String[] input = new String[]{"large coffee with extra milk, small coffee with special roast, bacon roll, orange juice"};
        //3.5,0.3, 2.5,0.9, 4.5, 3.95 = 15.65 - extra milk 0.3
        final BigDecimal expectedTotalAmount = round(new BigDecimal(15.35));
        final Receipt receipt = coffeeShopService.getReceipt(input);
        assertNotNull(receipt, "Can't be null");
        assertEquals(expectedTotalAmount, round(receipt.getTotalAmount()), "Total amount should be equals");
    }

    @Test
    public void testItShouldReturnReceiptWhenAllProductsAreBeverage() {

        final String[] input = new String[]{"large coffee, small coffee, orange juice"};
        //3.5, 2.5, 3.95
        final BigDecimal expectedTotalAmount = round(new BigDecimal(9.95));
        final Receipt receipt = coffeeShopService.getReceipt(input);
        assertNotNull(receipt, "Can't be null");
        assertEquals(expectedTotalAmount, round(receipt.getTotalAmount()), "Total amount should be equals");
    }

    @Test
    public void testItShouldReturnReceiptWith1ExtraFreeBeverage() {

        final String[] input = new String[]{"large coffee, small coffee, orange juice,large coffee, small coffee"};
        //3.5, 2.5, 3.95, 3.5 , 2.5 (2.5 - small coffee is free )
        final BigDecimal expectedTotalAmount = round(new BigDecimal(13.45));
        final Receipt receipt = coffeeShopService.getReceipt(input);
        assertNotNull(receipt, "Can't be null");
        assertEquals(expectedTotalAmount, round(receipt.getTotalAmount()), "Total amount should be equals");
    }

    @Test
    public void itShouldThrowRuntimeExceptionInCaseWhenCannotFindProduct() {
        assertThrows(RuntimeException.class,
                () -> {
                    final String[] input = new String[]{"large coffee, foo product"};
                    coffeeShopService.getReceipt(input);
                });
    }
}
