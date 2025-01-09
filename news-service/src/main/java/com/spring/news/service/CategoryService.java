package com.spring.news.service;


import com.spring.news.dto.CategoryDto;
import com.spring.news.model.Category;
import com.spring.news.repository.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final ICategoryRepository categoryRepository;
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category save(CategoryDto category) {
        Optional<Category> located = categoryRepository.findByName(category.getName());
        if (located.isPresent()) {
            throw new IllegalArgumentException("Category name already exist");
        } else {
            Category newCategory = new Category();
            newCategory.setName(category.getName());
            return categoryRepository.save(newCategory);
        }

    }
}
