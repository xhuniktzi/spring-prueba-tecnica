package com.spring.news.repository;

import com.spring.news.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface INewsRepository extends JpaRepository<News, Long> {
    public Page<News> findByPublished(boolean published, Pageable pageable);
    public Page<News> findByCategoryId(Long categoryId, Pageable pageable);
}
