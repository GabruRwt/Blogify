package com.rohit.blogify.service;

import com.rohit.blogify.dto.BlogUserDTO;
import com.rohit.blogify.entity.BlogUser;
import com.rohit.blogify.repository.BlogUserRepository;
import com.rohit.blogify.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BlogUserService {

    @Autowired
    private BlogUserRepository blogUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JWTUtil jwtUtil;

    ModelMapper modelMapper= new ModelMapper();

    @Transactional
    public BlogUserDTO registerNewUser(BlogUserDTO blogUserDTO){
        BlogUser blogUser=new BlogUser();
        modelMapper.map(blogUserDTO,blogUser);
        blogUser.setPassword(passwordEncoder.encode(blogUserDTO.getPassword()));
        blogUserRepository.save(blogUser);
        return blogUserDTO;
    }

    public String loginUser(BlogUserDTO blogUserDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(blogUserDTO.getUsername(),blogUserDTO.getPassword()));
        UserDetails userDetails= userDetailsService.loadUserByUsername(blogUserDTO.getUsername());
        return jwtUtil.generateToken(userDetails);
    }

    public BlogUser findByUserName(String username){
        return blogUserRepository.findByUsername(username);
    }

    public void save(BlogUser blogUser){
        blogUserRepository.save(blogUser);
    }
}
