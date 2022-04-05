package com.example.service;

import com.example.domain.Receipt;

public interface CoffeeShopService {

    Receipt getReceipt(String[] orderRawInput);

    String buildReceiptToPrint(Receipt receipt);
}
