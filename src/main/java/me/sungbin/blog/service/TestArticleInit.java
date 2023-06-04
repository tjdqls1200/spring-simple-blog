package me.sungbin.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sungbin.blog.controller.dto.AddArticleRequest;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestArticleInit {

    private final BlogService blogService;

    @EventListener(ApplicationReadyEvent.class)
    public void initArticles() {
        blogService.save(new AddArticleRequest("제목1", "내용1"));
        blogService.save(new AddArticleRequest("제목2", "내용2"));
        blogService.save(new AddArticleRequest("제목3", "내용3"));
    }
}
