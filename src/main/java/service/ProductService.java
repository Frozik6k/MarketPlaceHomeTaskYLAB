package service;

import model.Product;
import repository.ProductRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.createProduct(product);
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }

    public void removeProduct(String name) {
        productRepository.getProduct(name).ifPresent(productRepository::deleteProduct);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.getProduct(name);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public int getProductCount() {
        return productRepository.getAllProducts().size();
    }

    public SearchResult searchProducts(String name, String category, String brand,
                                       BigDecimal minPrice, BigDecimal maxPrice,
                                       Map<String, String> parameters) {
        Map<String, String> cleanedParameters = parameters == null ? Map.of() : Map.copyOf(parameters);

        List<Product> filtered = productRepository.getAllProducts().stream()
                .filter(product -> matches(product, name, category, brand, minPrice, maxPrice, cleanedParameters))
                .collect(Collectors.toCollection(ArrayList::new));
        List<Product> snapshot = List.copyOf(filtered);
        return new SearchResult(snapshot);
    }

    private boolean matches(Product product, String name, String category, String brand,
                            BigDecimal minPrice, BigDecimal maxPrice,
                            Map<String, String> parameters) {
        if (name != null && !name.isBlank()) {
            String productName = product.getName();
            if (productName == null || !productName.toLowerCase().contains(name.toLowerCase())) {
                return false;
            }
        }
        if (category != null && !category.isBlank() && !equalsIgnoreCase(product.getCategory(), category)) {
            return false;
        }
        if (brand != null && !brand.isBlank() && !equalsIgnoreCase(product.getBrand(), brand)) {
            return false;
        }
        if (minPrice != null) {
            if (product.getPrice() == null || product.getPrice().compareTo(minPrice) < 0) {
                return false;
            }
        }
        if (maxPrice != null) {
            if (product.getPrice() == null || product.getPrice().compareTo(maxPrice) > 0) {
                return false;
            }
        }
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String value = product.getParameters().get(entry.getKey());
            if (value == null || !value.equalsIgnoreCase(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    private boolean equalsIgnoreCase(String left, String right) {
        if (left == null || right == null) {
            return false;
        }
        return left.equalsIgnoreCase(right);
    }

    public record SearchResult(List<Product> products) {
    }
}
