package com.rohit.blogify.repository;

import com.rohit.blogify.entity.Comment;
import com.rohit.blogify.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CommentRepository extends MongoRepository<Comment,String> {
}
