package com.example.myapp.service;

import com.example.myapp.dao.CategoryRepository;
import com.example.myapp.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public  Optional<Category> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category;
    }

    @Transactional
    public Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);

        return categoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(Category category, String name) {
        category.setName(name);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
}
