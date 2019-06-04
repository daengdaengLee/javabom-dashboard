package io.github.daengdaenglee.javabomboardwas.services;

import io.github.daengdaenglee.javabomboardwas.entities.Article;
import io.github.daengdaenglee.javabomboardwas.repositories.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ArticleService.class
})
public class ArticleServiceTests {
    @MockBean
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Test
    public void constructorWithArticleRepository() {
        new ArticleService(articleRepository);
    }

    @Test
    public void getAllArticlesSuccessReturnMoreThanOneArticle() throws IOException {
        // given
        Article article1 = new Article("1", "article 1 title", "article 1 body");
        Article article2 = new Article("2", "article 2 title", "article 2 body");

        List<Article> expected = Arrays.asList(article1, article2);

        given(articleRepository.selectAll()).willReturn(expected);

        // when
        List<Article> received = articleService.getAllArticles();

        // then
        assertThat(received).isEqualTo(expected);
    }

    @Test
    public void getAllArticlesSuccessReturnEmptyArticleList() throws IOException {
        // given
        List<Article> expected = new ArrayList<>();

        given(articleRepository.selectAll()).willReturn(expected);

        // when
        List<Article> received = articleService.getAllArticles();

        // then
        assertThat(received).isEqualTo(expected);
    }
}
