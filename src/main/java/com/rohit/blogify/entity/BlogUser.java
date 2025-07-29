package com.rohit.blogify.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "blog_users")
public class BlogUser {

    @Id
    private String id;

    @NonNull
    private String username;

    @Indexed(unique = true)
    private String mail;

    @NonNull
    private String password;

    private List<String> roles;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @DBRef
    @JsonIgnore
    private List<Post> postList;

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
