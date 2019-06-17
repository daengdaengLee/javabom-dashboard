package io.github.daengdaenglee.javabomboardwas.repository;

import io.github.daengdaenglee.javabomboardwas.entity.article.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
