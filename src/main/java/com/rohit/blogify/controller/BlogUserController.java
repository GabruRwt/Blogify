package com.rohit.blogify.controller;


import com.rohit.blogify.dto.BlogUserDTO;
import com.rohit.blogify.entity.BlogUser;
import com.rohit.blogify.service.BlogUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog-user")
@Slf4j
public class BlogUserController {

    @Autowired
    private BlogUserService blogUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody BlogUserDTO blogUserDTO){
        try{
       BlogUserDTO blogUser= blogUserService.registerNewUser(blogUserDTO);
       return new ResponseEntity<>(blogUser, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login-user")
    public ResponseEntity<?> loginUser(@RequestBody BlogUserDTO blogUserDTO){
        try{
            String token= blogUserService.loginUser(blogUserDTO);
            return new ResponseEntity<>(token,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
