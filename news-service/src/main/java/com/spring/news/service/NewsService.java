package com.spring.news.service;

import com.spring.news.dto.NewsDto;
import com.spring.news.model.Category;
import com.spring.news.model.News;
import com.spring.news.model.User;
import com.spring.news.repository.ICategoryRepository;
import com.spring.news.repository.INewsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
    private final INewsRepository newsRepository;
    private final ICategoryRepository categoryRepository;


    public NewsService(INewsRepository newsRepository, ICategoryRepository categoryRepository) {
        this.newsRepository = newsRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<News> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return newsRepository.findByPublished(true,pageable);
    }

    public Page<News>search(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return newsRepository.findByCategoryId(categoryId, pageable);
    }

    public Optional<News> findById(long id) {
        return newsRepository.findById(id);
    }

    public News save(NewsDto newsDto) {
        News newsEntity = new News();
        newsEntity.setTitle(newsDto.getTitle());
        newsEntity.setContent(newsDto.getContent());
        newsEntity.setPublished(newsDto.isPublished());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Category> category = categoryRepository.findById(newsDto.getCategory_id());

        if (category.isPresent()) {
            Category categoryEntity = category.get();
            newsEntity.setCategory(categoryEntity);
        } else {
            throw new IllegalArgumentException("Category not found");
        }

        newsEntity.setAuthor_username(user.getUsername());

        return newsRepository.save(newsEntity);
    }

    public News update(Long id, NewsDto newsDto) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            News newsEntity = news.get();
            newsEntity.setTitle(newsDto.getTitle());
            newsEntity.setContent(newsDto.getContent());
            newsEntity.setPublished(newsDto.isPublished());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            Optional<Category> category = categoryRepository.findById(newsDto.getCategory_id());
            if (category.isPresent()) {
                Category categoryEntity = category.get();
                newsEntity.setCategory(categoryEntity);
            } else {
                throw new IllegalArgumentException("Category not found");
            }
            newsEntity.setAuthor_username(user.getUsername());
            return newsRepository.save(newsEntity);
        } else {
            throw new IllegalArgumentException("News not found");
        }
    }

    public void delete(Long id) {
        Optional<News> news = newsRepository.findById(id);
        if (news.isPresent()) {
            newsRepository.delete(news.get());
        } else {
            throw new IllegalArgumentException("News not found");
        }
    }
}
