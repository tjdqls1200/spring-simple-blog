package me.sungbin.blog.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.sungbin.blog.domain.Article;

@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;

    private String content;

    public Article toEntity() {
        return new Article(title, content);
    }
}
