package com.example.repository.impl;

import com.example.repository.ProductRepository;
import com.example.domain.Product;

import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private DataBase dataBase = new DataBase();

    @Override
    public Optional<Product> findProductByName(String name) {
        return dataBase.getAvailableProducts().stream().filter(p -> p.getName().equalsIgnoreCase(name)
                || p.getName().toLowerCase().contains(name.toLowerCase())).findFirst();

    }
}
