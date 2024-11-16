package com.krnelx.springcartproductapp.repository;

import com.krnelx.springcartproductapp.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class ProductRepository {

    private final Map<Long, Product> productMap = new HashMap<>();

    @PostConstruct
    public void init() {
        productMap.put(1L, new Product(1L, "Product A", BigDecimal.valueOf(100.0)));
        productMap.put(2L, new Product(2L, "Product B", BigDecimal.valueOf(200.0)));
        productMap.put(3L, new Product(3L, "Product C", BigDecimal.valueOf(300.0)));
        productMap.put(4L, new Product(4L, "Product D", BigDecimal.valueOf(400.0)));
        productMap.put(5L, new Product(5L, "Product E", BigDecimal.valueOf(500.0)));
    }

    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    public Product findById(Long id) {
        return productMap.get(id);
    }

    public void saveOrUpdate(Product product) {
        productMap.put(product.getId(), product);
    }

    public void deleteById(Long id) {
        productMap.remove(id);
    }
}