package com.spring.news.repository;

import com.spring.news.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface INewsRepository extends JpaRepository<News, Long> {
    Page<News> findByPublished(boolean published, Pageable pageable);

    @Query("SELECT n FROM News n WHERE " +
            "n.published = true AND " +
            "(:categoryId IS NULL OR n.category.id = :categoryId) AND " +
            "(:keyword IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
            "LOWER(n.content) LIKE LOWER(CONCAT('%',:keyword,'%')))")
    Page<News> search(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            Pageable pageable);
}
