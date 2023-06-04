package me.sungbin.blog.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateArticleRequest {
    @NotBlank
    private final String title;

    @NotBlank
    private final String content;
}
