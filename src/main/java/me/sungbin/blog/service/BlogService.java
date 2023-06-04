package me.sungbin.blog.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.blog.controller.dto.AddArticleRequest;
import me.sungbin.blog.repository.BlogRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepositoryImpl blogRepository;

    @Transactional
    public Long save(final AddArticleRequest articleRequest) {
        return blogRepository.save(articleRequest.toEntity()).getId();
    }
}
