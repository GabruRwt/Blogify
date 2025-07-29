package com.rohit.blogify.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerCustomConfig(){
        return new OpenAPI().info(
                new Info().title("Blogify- A User Blogging platform")
                        .description("Publish anything you like")
        );
    }
}
