package me.sungbin.blog.controller;

import lombok.RequiredArgsConstructor;
import me.sungbin.blog.controller.dto.AddArticleRequest;
import me.sungbin.blog.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class BlogRestController {
    private static final String ARTICLE_REQUEST_FORMAT = "/api/articles/%d";

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Void> addArticle(@RequestBody AddArticleRequest articleRequest) {
        final Long id = blogService.save(articleRequest);

        return ResponseEntity.created(URI.create(String.format(ARTICLE_REQUEST_FORMAT, id))).build();
    }
}
