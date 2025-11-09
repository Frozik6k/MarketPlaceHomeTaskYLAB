package model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class Product {
    private String name;
    private BigDecimal price;
    private String category;
    // key - тип параметра, value - значение параметра  ( пример: цвет - красный)
    private Map<String, String>  parameters;

    public Product() {
    }

    public Product(String name, BigDecimal price, Map<String, String> parameters) {
        this.name = name;
        this.price = price;
        this.parameters = parameters;
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
        return parameters;
    }

    public void setParameter(String attribute, String value) {
        parameters.put(attribute, value);
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
}
