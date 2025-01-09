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

}
