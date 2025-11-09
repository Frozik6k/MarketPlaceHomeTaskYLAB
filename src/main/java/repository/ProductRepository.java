package repository;

import model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void createProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(Product product);

    Optional<Product> getProduct(String productName);

    List<Product> getAllProducts();
}
