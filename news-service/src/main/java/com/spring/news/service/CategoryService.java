package com.spring.news.service;


import com.spring.news.dto.CategoryDto;
import com.spring.news.model.Category;
import com.spring.news.repository.ICategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final ICategoryRepository categoryRepository;
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
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

    public Category update(Long id, CategoryDto category) {
        Optional<Category> located = categoryRepository.findById(id);
        if (located.isPresent()) {
            Category categoryToUpdate = located.get();
            categoryToUpdate.setName(category.getName());
            return categoryRepository.save(categoryToUpdate);
        } else {
            throw new IllegalArgumentException("Category not found");
        }
    }

    public void delete(Long id) {
        Optional<Category> located = categoryRepository.findById(id);
        if (located.isPresent()) {
            categoryRepository.delete(located.get());
        } else {
            throw new IllegalArgumentException("Category not found");
        }
    }
}
