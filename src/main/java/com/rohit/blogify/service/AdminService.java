package com.rohit.blogify.service;

import com.mongodb.annotations.Sealed;
import com.rohit.blogify.dto.CommentDTO;
import com.rohit.blogify.entity.BlogUser;
import com.rohit.blogify.entity.Comment;
import com.rohit.blogify.entity.Post;
import com.rohit.blogify.repository.BlogUserRepository;
import com.rohit.blogify.repository.CommentRepository;
import com.rohit.blogify.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private BlogUserRepository blogUserRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public String deleteAnyComment(String commentId){

        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()-> new RuntimeException("Comment Not found"));

            commentRepository.deleteById(comment.getId());
            return "success";


    }

    public String deletePost(String postId){
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new RuntimeException("Comment Not found"));
        postRepository.deleteById(post.getId());
        return "success";
    }

    public Map<String, Integer> getAllDataCount(){
        Integer blogUserCount = blogUserRepository.findAll().size();
        Integer postCount= postRepository.findAll().size();
        Integer commentCount= commentRepository.findAll().size();

        Map<String ,Integer> dataCount = new HashMap<>();
        dataCount.put("No. of Blog User", blogUserCount);
        dataCount.put("No. of Posts", postCount);
        dataCount.put("No. of Comments", commentCount);

        return dataCount;
    }

}
