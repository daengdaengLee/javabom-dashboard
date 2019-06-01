package io.github.daengdaenglee.javabomboardwas.repositories;

import io.github.daengdaenglee.javabomboardwas.entities.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {
    public Article insert(Article article) {
        return article;
    }

    public Article selectById(String id) {
        return new Article(id, null,null);
    }

    public List<Article> selectAll() {
        return new ArrayList<>();
    }

    public Article update(Article article) {
        return article;
    }

    public void delete(String id) {
    }
}
