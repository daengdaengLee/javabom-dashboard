package io.github.daengdaenglee.javabomboardwas.services;

import io.github.daengdaenglee.javabomboardwas.repositories.ArticleRepository;
import org.junit.Test;

public class ArticleServiceTests {
    private static final ArticleRepository articleRepository = new ArticleRepository();

    @Test
    public void constructorWithArticleRepository() {
        new ArticleService(articleRepository);
    }
}
