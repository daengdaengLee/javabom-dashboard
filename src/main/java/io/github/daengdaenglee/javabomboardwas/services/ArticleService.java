package io.github.daengdaenglee.javabomboardwas.services;

import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getAllArticles() throws Exception;

    Article getArticleById(String id) throws Exception;

    Article makeNewArticle(Article article) throws Exception;

    Article changeArticle(Article article) throws Exception;

    Article deleteArticleById(String id) throws Exception;
}
