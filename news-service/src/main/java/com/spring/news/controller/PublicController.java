package com.spring.news.controller;

import com.spring.news.model.Category;
import com.spring.news.model.News;
import com.spring.news.service.CategoryService;
import com.spring.news.service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news/public")
@CrossOrigin("*")
public class PublicController {
    private final NewsService newsService;
    private final CategoryService categoryService;

    public PublicController(NewsService newsService, CategoryService categoryService) {
        this.newsService = newsService;
        this.categoryService = categoryService;
    }

    @GetMapping("/news/all")
    public ResponseEntity<Page<News>> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(newsService.findAll(page, size));
    }

    @GetMapping("/news/search")
    public ResponseEntity<Page<News>> search(
            @RequestParam(required = true) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(newsService.search(categoryId, page, size));
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<News> findById(@PathVariable Long id) {
        return newsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/all")
    public ResponseEntity<List<Category>> allCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(categoryService.findAll());
    }
}