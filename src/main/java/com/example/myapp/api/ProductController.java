package com.example.myapp.api;

import com.example.myapp.exception.NotFoundException;
import com.example.myapp.model.Product;
import com.example.myapp.service.ProductService;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@Api
@RequiredArgsConstructor
@RequestMapping(path = "/products")
public class ProductController {

    final  private ProductService productService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<Product> retrieveAllProducts(@PageableDefault(value=5, page=0) Pageable pageable) {
        final Page<Product> products = productService.getAllProducts(pageable);

        return products;
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product retrieveProduct(@PathVariable Long id) {
        final Product product = productService.getProductById(id)
            .orElseThrow(() -> new NotFoundException("product"));

        return product;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Valid ProductRequest request) {
        final Product product = productService.createProduct(request.getName(), request.getCurrency(), request.getPrice());

        return product;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable Long id, @RequestBody @Valid ProductController.ProductRequest request) {
        final Product product = productService.getProductById(id)
            .orElseThrow(() -> new NotFoundException("product"));

        productService.updateProduct(product, request.getName(), request.getCurrency(), request.getPrice());

        return product;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        final Product product = productService.getProductById(id)
            .orElseThrow(() -> new NotFoundException("product"));

        productService.deleteProduct(product);
    }

    @Getter
    @Setter
    static class ProductRequest {
        @NotNull(message = "name is required")
        private String name;
        @NotNull
        @Size(message = "Currency must be in ISO format", min = 3, max = 3)
        private String currency;
        @Min(0)
        private Double price;
    }
}
