package com.rohit.blogify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String id;

    private String content;

    private String postId;

    private LocalDateTime createdAt;
}
