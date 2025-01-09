package com.spring.news.controller;

import com.spring.news.dto.CategoryDto;
import com.spring.news.dto.NewsDto;
import com.spring.news.model.Category;
import com.spring.news.model.News;
import com.spring.news.service.CategoryService;
import com.spring.news.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news/api")
@CrossOrigin("*")
public class ApiController {
    private final CategoryService categoryService;
    private final NewsService newsService;

    public ApiController(CategoryService categoryService, NewsService newsService) {
        this.categoryService = categoryService;
        this.newsService = newsService;
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

    @PostMapping("/news/create")
    public ResponseEntity<News> createNews(@RequestBody NewsDto news) {
        try {
            return ResponseEntity.ok(newsService.save(news));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/news/update/{id}")
    public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody NewsDto news) {
        try {
            return ResponseEntity.ok(newsService.update(id, news));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/news/delete/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        try {
            newsService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
