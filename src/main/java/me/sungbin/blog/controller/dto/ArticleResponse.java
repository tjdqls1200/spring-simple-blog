package me.sungbin.blog.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.sungbin.blog.domain.Article;

@AllArgsConstructor
@Getter
public class ArticleResponse {
    private final String title;

    private final String content;

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(article.getTitle(), article.getContent());
    }
}
