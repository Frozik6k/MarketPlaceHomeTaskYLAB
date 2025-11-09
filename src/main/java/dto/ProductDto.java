package dto;

import java.math.BigDecimal;

record ProductDto(
    String name,
    String category,
    String brand,
    BigDecimal price

) {}
