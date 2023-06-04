package me.sungbin.blog.controller;

import lombok.RequiredArgsConstructor;
import me.sungbin.blog.controller.dto.AddArticleRequest;
import me.sungbin.blog.controller.dto.ArticleResponse;
import me.sungbin.blog.service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        return ResponseEntity.ok(blogService.findAll());
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.findOne(id));
    }
}
