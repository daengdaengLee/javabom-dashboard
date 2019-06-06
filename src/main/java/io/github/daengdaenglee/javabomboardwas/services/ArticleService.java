package io.github.daengdaenglee.javabomboardwas.services;

import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;

import java.io.IOException;
import java.util.List;

public interface ArticleService {
    List<Article> getAllArticles() throws IOException;

    Article getArticleById(String id) throws IOException;

    Article makeNewArticle(Article article) throws IOException;

    Article changeArticle(Article article) throws IOException;

    Article deleteArticleById(String id) throws IOException;
}
