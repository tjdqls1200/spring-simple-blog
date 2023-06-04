package me.sungbin.blog.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.sungbin.blog.domain.Article;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BlogRepositoryImpl {

    private final EntityManager em;

    public Article save(Article article) {
        em.persist(article);

        return article;
    }
}
