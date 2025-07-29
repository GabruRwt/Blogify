package com.rohit.blogify.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUserDTO {

    private String username;

    private String mail;

    private String password;

    private List<String> roles;

    public String getPassword() {
        return password;
    }
}
