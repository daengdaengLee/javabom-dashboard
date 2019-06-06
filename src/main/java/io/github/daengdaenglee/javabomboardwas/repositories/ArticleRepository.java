package io.github.daengdaenglee.javabomboardwas.repositories;

import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;

import java.io.IOException;
import java.util.List;

public interface ArticleRepository {
    Article insert(Article article) throws IOException;

    Article selectById(String id) throws IOException;

    List<Article> selectAll() throws IOException;

    Article update(Article article) throws IOException;

    Article deleteById(String id) throws IOException;
}
