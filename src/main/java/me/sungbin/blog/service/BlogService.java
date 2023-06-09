package me.sungbin.blog.service;

import lombok.RequiredArgsConstructor;
import me.sungbin.blog.common.exception.article.ArticleNotFoundException;
import me.sungbin.blog.controller.dto.AddArticleRequest;
import me.sungbin.blog.controller.dto.ArticleResponse;
import me.sungbin.blog.controller.dto.UpdateArticleRequest;
import me.sungbin.blog.domain.Article;
import me.sungbin.blog.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public Long save(final AddArticleRequest articleRequest) {
        return blogRepository.save(articleRequest.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public ArticleResponse findOne(Long id) {
        final Article article = blogRepository.findById(id).orElseThrow(ArticleNotFoundException::new);

        return ArticleResponse.from(article);
    }

    @Transactional(readOnly = true)
    public List<ArticleResponse> findAll() {
        return blogRepository.findAll()
                .stream()
                .map(ArticleResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteArticle(Long id) {
        blogRepository.findById(id).ifPresentOrElse(
                blogRepository::delete,
                ArticleNotFoundException::new);
    }

    @Transactional
    public void updateArticle(Long id, UpdateArticleRequest updateArticleRequest) {
        final Article article = blogRepository.findById(id).orElseThrow(ArticleNotFoundException::new);

        article.update(updateArticleRequest.getTitle(), updateArticleRequest.getContent());
    }
}
