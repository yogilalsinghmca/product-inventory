package com.example.myapp.service;

import com.example.myapp.dao.ProductRepository;
import com.example.myapp.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProductService  {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FixerIOClient fixerIOClient;

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(String name, String currency, double price) {

        double priceInEuro =  calculatePriceInEuro(currency, price);

        Product product = new Product();
        product.setName(name);
        product.setPrice(priceInEuro);

        return productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Product product, String name, String currency, double price) {

        double priceInEuro =  calculatePriceInEuro(currency, price);

        product.setName(name);
        product.setPrice(priceInEuro);
        productRepository.save(product);
    }

    private double calculatePriceInEuro(String currency, double price) {
        if (!Product.CURRENCY.equals(currency)) {
            price = fixerIOClient.convert(currency, Product.CURRENCY, price);
        }

        return (double) Math.round(price * 100) / 100;
    }

    @Transactional
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }


    public Page<Product> getAllProductsFor(Long categoryId, Pageable pageable) {
       return productRepository.getAllProductsFor(categoryId, pageable);
    }
}