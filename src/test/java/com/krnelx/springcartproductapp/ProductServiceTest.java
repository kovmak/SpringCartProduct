package com.krnelx.springcartproductapp;

import static junit.framework.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.krnelx.springcartproductapp.config.AppConfig;
import com.krnelx.springcartproductapp.model.Product;
import com.krnelx.springcartproductapp.repository.ProductRepository;
import com.krnelx.springcartproductapp.service.ProductServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ProductServiceTest {

    private ProductServiceImpl productService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        productService = context.getBean(ProductServiceImpl.class);
        productRepository = context.getBean(ProductRepository.class);

        productRepository.findAll().forEach(product -> productRepository.deleteById(product.getId()));
    }

    @Test
    void testGetProductList() {
        Product product = new Product(1L, "Test Product", BigDecimal.valueOf(10.0));
        productRepository.saveOrUpdate(product);

        List<Product> products = productService.getProductList();

        assertFalse(products.isEmpty());
        assertEquals("Test Product", products.get(0).getName());
    }

    @Test
    void testSaveOrUpdate() {
        Product product = new Product(1L, "New Product", BigDecimal.valueOf(15.0));
        productService.saveOrUpdate(product);

        Product retrievedProduct = productRepository.findById(1L);
        assertNotNull(retrievedProduct);
        assertEquals("New Product", retrievedProduct.getName());
    }

    @Test
    void testGetProductById() {
        Product product = new Product(1L, "Test Product", BigDecimal.valueOf(10.0));
        productRepository.saveOrUpdate(product);

        Product retrievedProduct = productService.getProductById(1L);

        assertNotNull(retrievedProduct);
        assertEquals("Test Product", retrievedProduct.getName());
    }

    @Test
    void testDeleteById() {
        Product product = new Product(1L, "Test Product", BigDecimal.valueOf(10.0));
        productRepository.saveOrUpdate(product);

        productService.deleteById(1L);

        Product deletedProduct = productRepository.findById(1L);
        assertNull(deletedProduct);
    }
}