package com.rohit.blogify.controller;


import com.rohit.blogify.dto.PostDTO;
import com.rohit.blogify.dto.ReadPostDTO;
import com.rohit.blogify.entity.Post;
import com.rohit.blogify.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/blog-user/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/all-post")
    public ResponseEntity<?> getAllPost(){
        List<ReadPostDTO> readPostDTOList= postService.getAllPost();


        if(Objects.nonNull(readPostDTOList)) return new ResponseEntity<>(readPostDTOList,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-post")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PostDTO post = postService.savePost(postDTO, authentication.getName());
            return new ResponseEntity<>(post, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-post")
    public ResponseEntity<?> updatePost(@RequestBody PostDTO postDTO){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            PostDTO post= postService.updatePost(postDTO, authentication.getName());
            if(Objects.nonNull(post)) {
                return new ResponseEntity<>(post, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(post,HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-post")
    public ResponseEntity<?> deletePost(@RequestBody String postId){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            boolean deleted= postService.deletePost(postId, authentication.getName());
            if(deleted) return new ResponseEntity<>(HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
