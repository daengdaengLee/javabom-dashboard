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
        return articleRepository.insert(new Article(null, title, body));
    }

    public Article changeArticle(Article article) throws IOException {
        return articleRepository.update(article);
    }

    public void deleteArticleById(String id) throws Exception {
        articleRepository.deleteById(id);
    }
}
