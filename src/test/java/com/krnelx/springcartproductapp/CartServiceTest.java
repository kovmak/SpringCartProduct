package com.krnelx.springcartproductapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.krnelx.springcartproductapp.config.AppConfig;
import com.krnelx.springcartproductapp.model.Cart;
import com.krnelx.springcartproductapp.model.Product;
import com.krnelx.springcartproductapp.service.CartServiceImpl;
import com.krnelx.springcartproductapp.service.ProductServiceImpl;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class CartServiceTest {

    private CartServiceImpl cartService;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        cartService = context.getBean(CartServiceImpl.class);
        productService = context.getBean(ProductServiceImpl.class);
    }

    @Test
    void testAddProductToCart() {
        Cart cart = cartService.getNewCart();
        Product product1 = productService.getProductById(1L);
        Product product2 = productService.getProductById(2L);

        cartService.addProduct(cart, product1, 2);
        cartService.addProduct(cart, product2, 3);

        assertEquals(2, cart.getCartMap().get(product1));
        assertEquals(3, cart.getCartMap().get(product2));
    }

    @Test
    void testRemoveProductFromCart() {
        Cart cart = cartService.getNewCart();
        Product product1 = productService.getProductById(1L);

        cartService.addProduct(cart, product1, 5);
        cartService.delProduct(cart, product1, 2);

        assertEquals(3, cart.getCartMap().get(product1));
    }

    @Test
    void testGetSumOfCart() {
        Cart cart = cartService.getNewCart();
        Product product1 = productService.getProductById(1L);
        Product product2 = productService.getProductById(2L);

        cartService.addProduct(cart, product1, 1);
        cartService.addProduct(cart, product2, 1);

        BigDecimal expectedSum = product1.getPrice().add(product2.getPrice());
        assertEquals(expectedSum, cartService.getSum(cart));
    }

    @Test
    void testGetProductQuantity() {
        Cart cart = cartService.getNewCart();
        Product product1 = productService.getProductById(1L);

        cartService.addProduct(cart, product1, 4);
        Assertions.assertEquals(4, cartService.getProductQuantity(cart, product1));
    }

    @Test
    void testProductNotInCartReturnsZero() {
        Cart cart = cartService.getNewCart();
        Product product2 = productService.getProductById(2L);

        Assertions.assertEquals(0, cartService.getProductQuantity(cart, product2));
    }
}