package com.spring.news.service;

import com.spring.news.model.News;
import com.spring.news.repository.INewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    private final INewsRepository newsRepository;

    public NewsService(INewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Page<News> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return newsRepository.findByPublished(true,pageable);
    }

    public Page<News>search(String search, long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return  newsRepository.search(search,categoryId,pageable);
    }

    public Optional<News> findById(long id) {
        return newsRepository.findById(id);
    }
}
