package com.example.myapp.api;

import com.example.myapp.model.Category;
import com.example.myapp.model.Product;
import com.example.myapp.service.ProductService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class)
@EnableSpringDataWebSupport
public class ProductControllerTest {

    private static final String URL = "/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testRetrieveAllProducts() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("C1");
        Product product1 = new Product();
        product1.setId(10L);
        product1.setName("P1");
        product1.setPrice(100.00);
        product1.setCategories(Collections.singleton(category));
        Product product2 = new Product();
        product2.setId(11L);
        product2.setName("P2");
        product2.setPrice(130.67);
        product2.setCategories(Collections.singleton(category));

        Mockito.when(productService.getProductById(10L)).thenReturn(Optional.of(product1));
        Mockito.when(productService.getProductById(11L)).thenReturn(Optional.of(product2));
        Mockito.when(productService.getAllProducts(Mockito.any())).thenReturn(
                new PageImpl<>(Arrays.asList(product1, product2), PageRequest.of(2, 20), 100)
        );

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", Matchers.is("P1")))
                .andExpect(jsonPath("$.content[0].price", Matchers.is(100.0)))
                .andExpect(jsonPath("$.content[0].categories", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.content[0].categories[0].name", Matchers.is("C1")))
                .andExpect(jsonPath("$.content[1].name", Matchers.is("P2")))
                .andExpect(jsonPath("$.content[1].price", Matchers.is(130.67)))
                .andExpect(jsonPath("$.content[1].categories", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.content[1].categories[0].name", Matchers.is("C1")))
                .andExpect(jsonPath("$.pageable.pageSize", Matchers.is(20)))
                .andExpect(jsonPath("$.totalElements", Matchers.is(100)))
                .andExpect(jsonPath("$.totalPages", Matchers.is(5)))
                .andExpect(jsonPath("$.number", Matchers.is(2)));
    }

    @Test
    public void testRetrieveProduct() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("C1");
        Product product = new Product();
        product.setId(10L);
        product.setName("P1");
        product.setPrice(100.00);
        product.setCategories(Collections.singleton(category));

        Mockito.when(productService.getProductById(Mockito.eq(10L))).thenReturn(Optional.of(product));

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{productid}", "10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("P1")))
                .andExpect(jsonPath("$.price", Matchers.is(100.0)))
                .andExpect(jsonPath("$.categories[0].name", Matchers.is("C1")))
        ;
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setId(10L);
        product.setName("P1");
        product.setPrice(100.00);
        product.setCategories(Collections.emptySet());

        Mockito.when(productService.createProduct(Mockito.eq("P1"), Mockito.eq("EUR"), Mockito.eq(100.00))).thenReturn(product);

        String request = "{ \"name\": \"P1\", \"currency\": \"EUR\", \"price\": 100.00 }";
        mockMvc.perform(post(URL).content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.is("P1")))
                .andExpect(jsonPath("$.price", Matchers.is(100.00)))
        ;

        Mockito.verify(productService).createProduct(Mockito.eq("P1"), Mockito.eq("EUR"), Mockito.eq(100.00));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product product = new Product();
        product.setId(10L);
        product.setName("P1");
        product.setPrice(100.00);
        product.setCategories(Collections.emptySet());

        Mockito.doNothing().when(productService).updateProduct(Mockito.eq(product), Mockito.eq("P1"), Mockito.eq("EUR"), Mockito.eq(100.00));
        Mockito.when(productService.getProductById(Mockito.eq(10L))).thenReturn(Optional.of(product));

        String request = "{ \"name\": \"P1\", \"currency\": \"EUR\", \"price\": 100.00 }";
        mockMvc.perform(put(URL + "/{productid}", "10").content(request).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("P1")))
                .andExpect(jsonPath("$.price", Matchers.is(100.00)));

        Mockito.verify(productService).updateProduct(Mockito.eq(product), Mockito.eq("P1"), Mockito.eq("EUR"), Mockito.eq(100.00));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        Product product = new Product();
        product.setId(10L);
        product.setName("P1");
        product.setPrice(100.00);
        product.setCategories(Collections.emptySet());

        Mockito.when(productService.getProductById(Mockito.eq(10L))).thenReturn(Optional.of(product));
        Mockito.doNothing().when(productService).deleteProduct(Mockito.eq(product));

        mockMvc.perform(delete(URL + "/{productid}", "10"))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(productService).deleteProduct(Mockito.eq(product));
    }

    @Test
    public void testResourceNotFound() throws Exception {
        mockMvc.perform(get(URL + "/{productid}", "-99999"))
                .andDo(print())
                .andExpect(status().isNotFound());

        mockMvc.perform(put(URL + "/{productid}", "-99999").content("{ \"name\": \"name\", \"currency\": \"EUR\", \"price\": 1.00 }").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        mockMvc.perform(delete(URL + "/{productid}", "-99999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
