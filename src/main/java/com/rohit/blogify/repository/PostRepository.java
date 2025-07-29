package com.rohit.blogify.repository;

import com.rohit.blogify.entity.BlogUser;
import com.rohit.blogify.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PostRepository extends MongoRepository<Post,String> {
}
