package com.spring.news.controller;

import com.spring.news.dto.CategoryDto;
import com.spring.news.model.Category;
import com.spring.news.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news/api")
@CrossOrigin("*")
public class ApiController {
    private final CategoryService categoryService;

    public ApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto category) {
        try {
            return ResponseEntity.ok(categoryService.save(category));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/category/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryDto category) {
        try {
            return ResponseEntity.ok(categoryService.update(id, category));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
