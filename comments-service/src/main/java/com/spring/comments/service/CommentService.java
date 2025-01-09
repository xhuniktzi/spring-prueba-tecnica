package com.spring.comments.service;

import com.spring.comments.dto.CommentDto;
import com.spring.comments.model.Comment;
import com.spring.comments.model.News;
import com.spring.comments.model.User;
import com.spring.comments.repository.ICommentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentService {

    private final ICommentRepository commentRepository;
    private final RestTemplate restTemplate;

    public CommentService(ICommentRepository commentRepository, RestTemplate restTemplate) {
        this.commentRepository = commentRepository;
        this.restTemplate = restTemplate;
    }

    public List<Comment> commentsByNews(Long newsId){
        try {
            News news = restTemplate.getForObject("http://NEWS/news/public/news/"+ newsId, News.class);

            if (news == null) {
                throw new IllegalArgumentException("News not found");
            } else {
                return commentRepository.findCommentsByNewsId(newsId);
            }

        } catch (RestClientException e) {
            throw new IllegalArgumentException("News not found");
        }

    }

    public Comment addComment(CommentDto comment){
        try {
            News news = restTemplate.getForObject("http://NEWS/news/public/news/"+ comment.getNewsId(), News.class);
            if (news == null) {
                throw new IllegalArgumentException("News not found");
            } else {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                User user = (User) authentication.getPrincipal();

                Comment newComment = new Comment();
                newComment.setComment(comment.getComment());
                newComment.setNewsId(comment.getNewsId());
                newComment.setAuthor_username(user.getUsername());

                return commentRepository.save(newComment);
            }
        } catch (RestClientException e) {
            throw new IllegalArgumentException("News not found");
        }
    }

    public void deleteComment(Long commentId){
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalArgumentException("Comment not found");
        }
    }
}
