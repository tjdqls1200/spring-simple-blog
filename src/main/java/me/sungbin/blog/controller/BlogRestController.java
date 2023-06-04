package me.sungbin.blog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sungbin.blog.controller.dto.AddArticleRequest;
import me.sungbin.blog.controller.dto.ArticleResponse;
import me.sungbin.blog.controller.dto.UpdateArticleRequest;
import me.sungbin.blog.service.BlogService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogRestController {
    private static final String ARTICLE_URL = "/api/articles/%d";

    private final BlogService blogService;

    @PostMapping("/api/articles")
    public ResponseEntity<Void> addArticle(@RequestBody AddArticleRequest addArticleRequest) {
        final Long id = blogService.save(addArticleRequest);

        return ResponseEntity.created(URI.create(String.format(ARTICLE_URL, id))).build();
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        return ResponseEntity.ok(blogService.findAll());
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id) {
        return ResponseEntity.ok(blogService.findOne(id));
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable Long id, @Valid @RequestBody UpdateArticleRequest updateArticleRequest) {
        blogService.updateArticle(id, updateArticleRequest);

        return ResponseEntity.ok()
                .header(HttpHeaders.LOCATION, String.format(ARTICLE_URL, id))
                .build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.deleteArticle(id);

        return ResponseEntity.ok().build();
    }
}
