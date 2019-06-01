package io.github.daengdaenglee.javabomboardwas.services;

import io.github.daengdaenglee.javabomboardwas.entities.Article;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    public List<Article> getAllArticles() {
        return new ArrayList<Article>();
    }

    public Article getArticleById(String id) {
        return new Article(id, null, null);
    }

    public Article makeNewArticle(String title, String body) {
        Article article = new Article(null, title, body);
        return article;
    }

    public Article changeArticle(Article article) {
        return article;
    }

    public Boolean deleteArticleById(String id) {
        return true;
    }
}
