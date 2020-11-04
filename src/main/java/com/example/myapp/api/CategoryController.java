package com.example.myapp.api;

import com.example.myapp.exception.NotFoundException;
import com.example.myapp.model.Category;
import com.example.myapp.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Api
@RequestMapping(path = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> retrieveAllCategories() {
        final List<Category> categories = categoryService.getAllCategories();

        return categories;
    }

    @GetMapping(path = "/{id}")
    public Category retrieveCategory(@PathVariable Long id) {
        final Category category = categoryService.getCategoryById(id)
            .orElseThrow(() -> new NotFoundException("category"));

        return category;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@RequestBody @Valid CategoryRequest request) {
        final Category category = categoryService.createCategory(request.getName());

        return category;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
        final Category category = categoryService.getCategoryById(id)
            .orElseThrow(() -> new NotFoundException("category"));

        categoryService.updateCategory(category, request.getName());

        return category;
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable Long id) {
        final Category category = categoryService.getCategoryById(id)
            .orElseThrow(() -> new NotFoundException("category"));

        categoryService.deleteCategory(category);
    }

    @Getter
    @Setter
    static class CategoryRequest {
        @NotNull(message = "name is required")
        private String name;
    }
}
