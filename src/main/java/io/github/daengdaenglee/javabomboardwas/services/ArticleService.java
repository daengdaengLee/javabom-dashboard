package io.github.daengdaenglee.javabomboardwas.services;

import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;
import io.github.daengdaenglee.javabomboardwas.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ArticleService {
    public final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() throws IOException {
        return articleRepository.selectAll();
    }

    public Article getArticleById(String id) throws IOException {
        return articleRepository.selectById(id);
    }

    public Article makeNewArticle(Article article) throws IOException {
        return articleRepository.insert(Article.builder().attributes(article.getAttributes()).build());
    }

    public Article changeArticle(Article article) throws IOException {
        return articleRepository.update(article);
    }

    public void deleteArticleById(String id) {
        articleRepository.deleteById(id);
    }
}
