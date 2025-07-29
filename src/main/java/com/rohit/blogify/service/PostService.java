package com.rohit.blogify.service;


import com.rohit.blogify.dto.CommentDTO;
import com.rohit.blogify.dto.PostDTO;
import com.rohit.blogify.dto.ReadPostDTO;
import com.rohit.blogify.entity.BlogUser;
import com.rohit.blogify.entity.Comment;
import com.rohit.blogify.entity.Post;
import com.rohit.blogify.repository.CommentRepository;
import com.rohit.blogify.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PostService {

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
            private CommentRepository commentRepository;

    ModelMapper modelMapper = new ModelMapper();

    public List<ReadPostDTO> getAllPost(){
        List<Post> postList=postRepository.findAll();
        List<Comment> comments=commentRepository.findAll();
        if(Objects.nonNull(postList) && !postList.isEmpty()){
            return postList.stream().map(post -> {
                // Filter comments for this post
                List<Comment> postComments = comments.stream()
                        .filter(comment -> comment.getPostId().getId().equals(post.getId()))
                        .toList();

                // Map Post to ReadPostDTO
                ReadPostDTO dto = modelMapper.map(post, ReadPostDTO.class);

                List<CommentDTO> postCommentsDTO=postComments.stream().map(comment -> modelMapper.map(comment,CommentDTO.class)).toList();
                // Set comments manually (assuming ReadPostDTO has a setComments method)
                dto.setCommentDTO(postCommentsDTO);

                return dto;
            }).toList();
        }
        return null;

    }

    @Transactional
    public PostDTO savePost(PostDTO postDTO, String username) throws Exception {
        try {
            Post post = modelMapper.map(postDTO, Post.class);
            post.setCreatedAt(LocalDateTime.now());
            BlogUser blogUser = blogUserService.findByUserName(username);
            post.setAuthor(blogUser);
            Post savedPost = postRepository.save(post);
            blogUser.getPostList().add(savedPost);
            blogUserService.save(blogUser);
            return modelMapper.map(savedPost, PostDTO.class);
        }
        catch (Exception e){
            log.error("Error while saving post", e);
            return null;
        }
    }

    @Transactional
    public PostDTO updatePost(PostDTO postDTO,String username){
        BlogUser blogUser= blogUserService.findByUserName(username);
        String id= postDTO.getId();
        List<Post> postList= blogUser.getPostList();
        boolean isAvailable=postList.stream().anyMatch(post -> post.getId().equals(id));
        if(isAvailable) {
            Post post = modelMapper.map(postDTO, Post.class);
            post.setCreatedAt(LocalDateTime.now());
            postRepository.save(post);
            return postDTO;
        }
        return null;
    }

    public boolean deletePost(String postId, String username){
        BlogUser blogUser= blogUserService.findByUserName(username);
        List<Post> userPostList= blogUser.getPostList();

        boolean ownsPost = userPostList.stream()
                .anyMatch(post -> post.getId().equals(postId));

        if (!ownsPost) {
            throw new RuntimeException("Unauthorized: This post doesn't belong to you");
        }

        List<Post> newPostList=userPostList.stream().filter(post -> !post.getId().equals(postId)).toList();
        blogUser.setPostList(newPostList);
        postRepository.deleteById(postId);
        blogUserService.save(blogUser);
        return true;
    }
}
