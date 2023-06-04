package me.sungbin.blog.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import me.sungbin.blog.domain.Article;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BlogRepository {

    private final EntityManager em;

    public Article save(Article article) {
        em.persist(article);

        return article;
    }

    @Transactional
    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a", Article.class)
                .getResultList();
    }
}
