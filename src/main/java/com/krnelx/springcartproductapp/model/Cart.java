package com.krnelx.springcartproductapp.model;

import java.util.HashMap;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
public class Cart {

    private Map<Product, Integer> cartMap = new HashMap<>();

    public void addProduct(Product product, Integer quantity) {
        cartMap.merge(product, quantity, Integer::sum);
    }

    public void delProduct(Product product, Integer quantity) {
        cartMap.computeIfPresent(product, (k, v) -> (v - quantity <= 0) ? null : v - quantity);
    }

    public BigDecimal getSum() {
        return cartMap.entrySet().stream()
            .map(e -> e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}