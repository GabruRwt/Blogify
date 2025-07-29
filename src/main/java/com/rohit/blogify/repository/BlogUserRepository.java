package com.rohit.blogify.repository;

import com.rohit.blogify.entity.BlogUser;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface BlogUserRepository extends MongoRepository<BlogUser,String> {

    BlogUser findByUsername(String username);

    BlogUser deleteByMail(String mail);
}
