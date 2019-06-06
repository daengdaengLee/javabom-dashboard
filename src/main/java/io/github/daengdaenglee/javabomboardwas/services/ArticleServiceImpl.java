package io.github.daengdaenglee.javabomboardwas.services;

import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;
import io.github.daengdaenglee.javabomboardwas.repositories.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    public final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() throws Exception {
        return articleRepository.selectAll();
    }

    public Article getArticleById(String id) throws Exception {
        return articleRepository.selectById(id);
    }

    public Article makeNewArticle(Article article) throws Exception {
        return articleRepository.insert(article);
    }

    public Article changeArticle(Article article) throws Exception {
        return articleRepository.update(article);
    }

    public Article deleteArticleById(String id) throws Exception {
        return articleRepository.deleteById(id);
    }
}
