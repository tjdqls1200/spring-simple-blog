package me.sungbin.blog;

import me.sungbin.blog.repository.BlogRepository;
import me.sungbin.blog.service.TestArticleInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    @Profile("local")
    public TestArticleInit testArticleInit(BlogRepository blogRepository) {
        return new TestArticleInit(blogRepository);
    }

}
