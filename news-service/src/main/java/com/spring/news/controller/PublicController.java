package com.spring.news.controller;

import com.spring.news.model.News;
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

    public PublicController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<News>> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(newsService.findAll(page, size));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<News>> search(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(newsService.search(search, categoryId, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> findById(@PathVariable Long id) {
        return newsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}