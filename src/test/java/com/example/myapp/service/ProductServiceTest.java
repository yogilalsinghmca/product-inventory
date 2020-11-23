package com.example.myapp.service;

import com.example.myapp.dao.ProductRepository;
import com.example.myapp.model.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    FixerIOClient fixerIOClient;

    @InjectMocks
    ProductService productService;


    @Test
    public void testCreateProduct() {

        Product expectedProduct = new Product();
        expectedProduct.setName("TV");
        expectedProduct.setPrice(233);
        Mockito.when(productRepository.save(Mockito.eq(expectedProduct))).thenReturn(expectedProduct);
        productService.createProduct("TV", "EUR", 233);
    }
}
