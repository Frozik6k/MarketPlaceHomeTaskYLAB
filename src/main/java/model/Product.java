package model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Product {
    private String name;
    private BigDecimal price;
    private String category;
    private String brand;
    // key - тип параметра, value - значение параметра  ( пример: цвет - красный)
    private Map<String, String> parameters = new HashMap<>();

    public Product() {
    }

    public Product(String name, BigDecimal price, Map<String, String> parameters) {
        this(name, price, null, null, parameters);
    }

    public Product(String name, BigDecimal price, String category, String brand, Map<String, String> parameters) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.brand = brand;
        if (parameters != null) {
            this.parameters.putAll(parameters);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters.clear();
        if (parameters != null) {
            this.parameters.putAll(parameters);
        }
    }

    public void setParameter(String attribute, String value) {
        parameters.put(attribute, value);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
