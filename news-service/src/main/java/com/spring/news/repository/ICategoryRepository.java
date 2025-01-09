package com.spring.news.repository;

import com.spring.news.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> findByName(String name);

}
