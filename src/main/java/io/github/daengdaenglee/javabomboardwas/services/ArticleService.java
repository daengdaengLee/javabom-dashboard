package io.github.daengdaenglee.javabomboardwas.services;

import io.github.daengdaenglee.javabomboardwas.entities.Article;
import io.github.daengdaenglee.javabomboardwas.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    public ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        return new ArrayList<Article>();
    }

    public Article getArticleById(String id) {
        return new Article(id, null, null);
    }

    public Article makeNewArticle(String title, String body) throws IOException {
        Article article = new Article(null, title, body);

        article = articleRepository.insert(article);

        return article;
    }

    public Article changeArticle(Article article) {
        return article;
    }

    public Boolean deleteArticleById(String id) {
        return true;
    }
}
