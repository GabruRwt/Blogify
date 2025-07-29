package com.rohit.blogify.controller;

import com.rohit.blogify.dto.BlogUserDTO;
import com.rohit.blogify.dto.CommentDTO;
import com.rohit.blogify.repository.BlogUserRepository;
import com.rohit.blogify.service.AdminService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BlogUserRepository blogUserRepository;

    ModelMapper modelMapper= new ModelMapper();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<?> getAllBlogUser() throws Exception {
        try {
             List<BlogUserDTO> blogUserDTOList = blogUserRepository.findAll().stream()
                            .map(blogUser ->
                                modelMapper.map(blogUser,BlogUserDTO.class))
                            .toList();
            return new ResponseEntity<>(blogUserDTOList,HttpStatus.OK);
        }
        catch (Exception e){
            throw new Exception("some error", e);
        }
    }

    @DeleteMapping("/delete-comment")
    public ResponseEntity<?> deleteComment(@RequestBody String commentId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String response= adminService.deleteAnyComment(commentId);
        if(!response.isBlank())return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>("some error occured", HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/delete-post")
    public ResponseEntity<?> deletePost(@RequestBody String postId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String response= adminService.deletePost(postId);
        if(!response.isBlank())return new ResponseEntity<>(response,HttpStatus.OK);
        return new ResponseEntity<>("some error occured", HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/get-all-data-count")
    public ResponseEntity<?> getAllDataCount(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        Map<String, Integer> dataCount = adminService.getAllDataCount();
        if(dataCount.size()==0 || Objects.isNull(dataCount))return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dataCount,HttpStatus.OK);
    }





}
