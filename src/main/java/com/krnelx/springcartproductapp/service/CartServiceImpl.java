package com.krnelx.springcartproductapp.service;

import com.krnelx.springcartproductapp.model.Cart;
import com.krnelx.springcartproductapp.model.Product;
import com.krnelx.springcartproductapp.repository.ProductRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class CartServiceImpl implements CartService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Cart getNewCart() {
        return new Cart();
    }

    @Override
    public void addProduct(Cart cart, Product product, Integer quantity) {
        cart.addProduct(product, quantity);
    }

    @Override
    public void addProduct(Cart cart, Long prodId, Integer quantity) {
        Product product = productRepository.findById(prodId);
        if (product != null) {
            addProduct(cart, product, quantity);
        }
    }

    @Override
    public void delProduct(Cart cart, Product product, Integer quantity) {
        cart.delProduct(product, quantity);
    }

    @Override
    public BigDecimal getSum(Cart cart) {
        return cart.getSum();
    }

    @Override
    public void printCart(Cart cart) {
        cart.getCartMap().forEach((product, qty) ->
            System.out.println(product.getName() + " x" + qty + " = " + product.getPrice().multiply(BigDecimal.valueOf(qty))));
    }

    @Override
    public int getProductQuantity(Cart cart, Product product) {
        return cart.getCartMap().getOrDefault(product, 0);
    }

    @Override
    public int getProductQuantity(Cart cart, Long prodId) {
        Product product = productRepository.findById(prodId);
        return getProductQuantity(cart, product);
    }
}