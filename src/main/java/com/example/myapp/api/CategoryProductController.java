package com.example.myapp.api;

import com.example.myapp.exception.NotFoundException;
import com.example.myapp.model.Product;
import com.example.myapp.service.CategoryService;
import com.example.myapp.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("categories/{categoryId}/products")
public class CategoryProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @ApiOperation(value = "retrieve all products for given categoryId")
    @GetMapping
    public Page<Product> getProductsFor(@PathVariable("categoryId") Long categoryId, Pageable pageable) {
            categoryService.getCategoryById(categoryId).orElseThrow(()-> new NotFoundException("Category"));
            Page<Product> products = productService.getAllProductsFor(categoryId, pageable);

            return  products;
    }
}
