package me.sungbin.blog.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.blog.domain.Article;
import me.sungbin.blog.repository.BlogRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
public class TestArticleInit {

    private final BlogRepository blogRepository;

    @TransactionalEventListener(ApplicationReadyEvent.class)
    public void initArticles() {
        blogRepository.save(new Article("제목1", "내용1"));
        blogRepository.save(new Article("제목2", "내용2"));
        blogRepository.save(new Article("제목3", "내용3"));

    }
}
