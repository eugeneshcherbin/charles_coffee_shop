package com.example.util;

import com.example.domain.Order;
import com.example.domain.Product;
import com.example.domain.Receipt;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.domain.Product.DEFAULT_CURRENCY;
import static com.example.util.CoffeeShopUtil.THRESHOLD_LENGTH;
import static com.example.util.CoffeeShopUtil.round;
import static org.junit.jupiter.api.Assertions.*;

public class CoffeeShopUtilTest {


    @Test
    public void itShouldTapName() {
        final String test = "1";
        assertEquals(1, test.length(), "Length should be 1");
        assertEquals(THRESHOLD_LENGTH, CoffeeShopUtil.tapName(test).length(),
                String.format("Length of string should be %d", THRESHOLD_LENGTH));
    }

    @Test
    public void itShouldBuildReceiptAsString(){
        final BigDecimal totalAmount = new BigDecimal(10.00);
        final List<Order> orders = new ArrayList<>();
        final Receipt receipt = new Receipt("1", orders, totalAmount, DEFAULT_CURRENCY, new Date());
        final String receiptAsString = CoffeeShopUtil.buildReceiptToPrint(receipt);
        assertNotNull(receiptAsString);
        System.out.println(receiptAsString);
        assertTrue(receiptAsString.contains("RECEIPT"));
        assertTrue(receiptAsString.contains("Receipt Date:"));
        assertTrue(receiptAsString.contains(String.format(" Total Amount : %s %s",
                round(totalAmount), DEFAULT_CURRENCY)),
                "It should contains string : "+String.format(" Total Amount : %s %s", round(totalAmount), DEFAULT_CURRENCY));
    }
}
