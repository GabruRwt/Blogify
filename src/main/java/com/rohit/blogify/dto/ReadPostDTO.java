package com.rohit.blogify.dto;

import com.rohit.blogify.entity.BlogUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadPostDTO {

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private List<CommentDTO> commentDTO;

}
