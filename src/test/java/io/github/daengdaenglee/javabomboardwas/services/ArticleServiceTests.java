package io.github.daengdaenglee.javabomboardwas.services;

import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;
import io.github.daengdaenglee.javabomboardwas.entities.articles.Attributes;
import io.github.daengdaenglee.javabomboardwas.entities.articles.Links;
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
        Article article1 = Article.builder()
                .id("1")
                .attributes(Attributes.builder().title("article 1 title").body("article 1 body").build())
                .type("articles")
                .links(new Links("/articles/1"))
                .build();
        Article article2 = Article.builder()
                .id("2")
                .attributes(Attributes.builder().title("article 2 title").body("article 2 body").build())
                .type("articles")
                .links(new Links("/articles/2"))
                .build();

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

    @Test
    public void getArticleByIdSuccessReturnOneArticle() throws IOException {
        // given
        String id = "12345";
        Article expected = Article.builder()
                .id(id)
                .attributes(Attributes.builder().build())
                .links(new Links("/articles/" + id))
                .build();

        given(articleRepository.selectById(id)).willReturn(expected);

        // when
        Article received = articleRepository.selectById(id);

        // then
        assertThat(received).isEqualTo(expected);
    }

    @Test
    public void makeNewArticleSuccessReturnCreatedArticle() throws IOException {
        // given
        Attributes attributes = Attributes.builder()
                .body("This is a test article.")
                .title("Test article")
                .build();
        Article inputArticle = Article.builder()
                .attributes(attributes)
                .build();
        Article expected = Article.builder()
                .id("1234")
                .attributes(attributes)
                .links(new Links("/articles/1234"))
                .build();

        given(articleRepository.insert(inputArticle)).willReturn(expected);

        // when
        Article received = articleRepository.insert(inputArticle);

        // then
        assertThat(received).isEqualTo(expected);
    }
}
