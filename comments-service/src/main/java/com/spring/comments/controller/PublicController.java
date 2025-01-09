package com.spring.comments.controller;

import com.spring.comments.model.Comment;
import com.spring.comments.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments/public")
@CrossOrigin("*")
public class PublicController {
    private final CommentService commentService;

    public PublicController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<List<Comment>> getComment(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(commentService.commentsByNews(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
