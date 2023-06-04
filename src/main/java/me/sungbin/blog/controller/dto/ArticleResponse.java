package me.sungbin.blog.controller.dto;

import lombok.Getter;
import me.sungbin.blog.domain.Article;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;

    public ArticleResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(article.getTitle(), article.getContent());
    }
}
