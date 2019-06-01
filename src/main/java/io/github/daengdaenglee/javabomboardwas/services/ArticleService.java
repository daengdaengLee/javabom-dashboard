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
}
