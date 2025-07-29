package com.rohit.blogify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private String id;

    private String title;

    private String content;

    private CommentDTO comments;
}
