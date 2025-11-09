package repository.impl;

import model.Product;
import repository.ProductRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepositoryImpl implements ProductRepository {

    Map<String, Product> products = new HashMap<>();

    @Override
    public void createProduct(Product product) {
        if (products.containsKey(product.getName())) {
            throw new IllegalArgumentException("Данный товар уже существует");
        } else {
            products.put(product.getName(), product);
        }
    }

    @Override
    public void updateProduct(Product product) {
        if (products.containsKey(product.getName())) {
            products.put(product.getName(), product);
        } else {
            throw new IllegalArgumentException("Такого товара не существует");
        }
    }

    @Override
    public void deleteProduct(Product product) {
        products.remove(product.getName());
    }

    @Override
    public Product getProduct(String productName) {
        if (products.containsKey(productName)) {
            return products.get(productName);
        } else {
            throw new IllegalArgumentException("Такого товара не существует");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return products.values().stream().toList();
    }
}
