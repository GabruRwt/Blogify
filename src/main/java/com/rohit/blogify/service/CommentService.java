package com.rohit.blogify.service;


import com.rohit.blogify.dto.CommentDTO;
import com.rohit.blogify.entity.BlogUser;
import com.rohit.blogify.entity.Comment;
import com.rohit.blogify.entity.Post;
import com.rohit.blogify.repository.BlogUserRepository;
import com.rohit.blogify.repository.CommentRepository;
import com.rohit.blogify.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private BlogUserRepository blogUserRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public CommentDTO addPostComment(CommentDTO commentDTO, String username){
        BlogUser blogUser= blogUserRepository.findByUsername(username);

        Post post= postRepository.findById(commentDTO.getPostId()).orElseThrow(
                ()-> new RuntimeException("Post Not found"));

        Comment comment= new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setPostId(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUserId(blogUser);

        commentRepository.save(comment);
        new ModelMapper().map(comment,commentDTO);

        return commentDTO;
    }

    public String deleteOwnComment(CommentDTO commentDTO, String username){
        BlogUser blogUser= blogUserRepository.findByUsername(username);

        Post post= postRepository.findById(commentDTO.getPostId()).orElseThrow(
                ()-> new RuntimeException("Post Not found"));

        Comment comment=commentRepository.findById(commentDTO.getId()).orElseThrow(
                ()-> new RuntimeException("Comment Not found"));

        if(comment.getUserId().getId().equals(blogUser.getId())) {
            commentRepository.deleteById(comment.getId());
            return "success";
        }
        return null;

    }

}
