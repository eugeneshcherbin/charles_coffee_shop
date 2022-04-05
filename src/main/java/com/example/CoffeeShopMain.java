package com.example;

import com.example.repository.impl.ProductRepositoryImpl;
import com.example.service.CoffeeShopService;
import com.example.service.impl.CoffeeShopServiceImpl;


public class CoffeeShopMain {


    public static void main(String[] args) {
        final CoffeeShopService coffeeShopService = new CoffeeShopServiceImpl(new ProductRepositoryImpl());
        if (args == null || args.length == 0) {
            System.out.println("Please select any product");
            System.exit(0);
        }
        System.out.println(coffeeShopService.buildReceiptToPrint(coffeeShopService.getReceipt(args)));
    }
}
