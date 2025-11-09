package repository;

import model.Product;

import java.util.List;

public interface ProductRepository {
    void createProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(Product product);
    Product getProduct(String productName);
    List<Product> getAllProducts();
}
