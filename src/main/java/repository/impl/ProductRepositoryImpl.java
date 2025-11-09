package repository.impl;

import model.Product;
import repository.ProductRepository;

import java.util.*;

public class ProductRepositoryImpl implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    public ProductRepositoryImpl() {
    }

    @Override
    public void createProduct(Product product) {
        if (products.containsKey(product.getName())) {
            throw new IllegalArgumentException("Данный товар уже существует");
        }
        products.put(product.getName(), product);
    }

    @Override
    public void updateProduct(Product product) {
        if (!products.containsKey(product.getName())) {
            throw new IllegalArgumentException("Такого товара не существует");
        }
        products.put(product.getName(), product);
    }

    @Override
    public void deleteProduct(Product product) {
        products.remove(product.getName());
    }

    @Override
    public Optional<Product> getProduct(String productName) {
        return Optional.ofNullable(products.get(productName));
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

}
