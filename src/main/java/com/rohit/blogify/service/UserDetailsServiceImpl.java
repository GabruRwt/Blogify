package com.rohit.blogify.service;


import com.rohit.blogify.entity.BlogUser;
import com.rohit.blogify.repository.BlogUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private BlogUserRepository blogUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BlogUser blogUser = blogUserRepository.findByUsername(username);

        if (Objects.nonNull(blogUser)) {
            return User.builder().username(blogUser.getUsername())
                    .password(blogUser.getPassword())
                    .roles(blogUser.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username:" +username);
    }
}
