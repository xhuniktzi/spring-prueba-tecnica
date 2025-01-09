package com.spring.comments.repository;

import com.spring.comments.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findCommentsByNewsId(Long id);
}
