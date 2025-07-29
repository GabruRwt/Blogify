package com.rohit.blogify.controller;


import com.rohit.blogify.dto.CommentDTO;
import com.rohit.blogify.entity.Comment;
import com.rohit.blogify.service.CommentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog-user/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add-comments")
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        CommentDTO commentDTO1= commentService.addPostComment(commentDTO,authentication.getName());
        return new ResponseEntity<>(commentDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/delete-comments")
    public ResponseEntity<?> deleteComment(@RequestBody CommentDTO commentDTO){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String response= commentService.deleteOwnComment(commentDTO, authentication.getName());
        if(!response.isBlank())return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>("some error occured", HttpStatus.NOT_MODIFIED);
    }
}
