package io.github.daengdaenglee.javabomboardwas.repositories;

import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;

import java.util.List;

public interface ArticleRepository {
    Article insert(Article article) throws Exception;

    Article selectById(String id) throws Exception;

    List<Article> selectAll() throws Exception;

    Article update(Article article) throws Exception;

    Article deleteById(String id) throws Exception;
}
