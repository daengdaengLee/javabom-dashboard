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
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ArticleServiceImpl.class
})
public class ArticleServiceTests {
    @MockBean
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Test
    public void constructorWithArticleRepository() {
        new ArticleServiceImpl(articleRepository);
    }

    @Test
    public void getAllArticlesSuccessReturnMoreThanOneArticle() throws Exception {
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
    public void getAllArticlesSuccessReturnEmptyArticleList() throws Exception {
        // given
        List<Article> expected = new ArrayList<>();

        given(articleRepository.selectAll()).willReturn(expected);

        // when
        List<Article> received = articleService.getAllArticles();

        // then
        assertThat(received).isEqualTo(expected);
    }

    @Test
    public void getAllArticlesFailThrowIOException() throws Exception {
        // given
        IOException expected = new IOException("Failed to read file");

        given(articleRepository.selectAll()).willThrow(expected);

        // when
        Throwable received = catchThrowable(() -> articleService.getAllArticles());

        // then
        assertThat(received).isInstanceOf(expected.getClass());
        assertThat(received).hasMessage(expected.getMessage());
    }

    @Test
    public void getArticleByIdSuccessReturnOneArticle() throws Exception {
        // given
        String id = "12345";
        Article expected = Article.builder()
                .id(id)
                .attributes(Attributes.builder().build())
                .links(new Links("/articles/" + id))
                .build();

        given(articleRepository.selectById(id)).willReturn(expected);

        // when
        Article received = articleService.getArticleById(id);

        // then
        assertThat(received).isEqualTo(expected);
    }

    @Test
    public void getArticleByIdFailThrowIOException() throws Exception {
        // given
        String id = "1234";
        IOException expected = new IOException("Failed to read file");

        given(articleRepository.selectById(id)).willThrow(expected);

        // when
        Throwable received = catchThrowable(() -> articleService.getArticleById(id));

        // then
        assertThat(received).isInstanceOf(expected.getClass());
        assertThat(received).hasMessage(expected.getMessage());
    }

    @Test
    public void makeNewArticleSuccessReturnCreatedArticle() throws Exception {
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
        Article received = articleService.makeNewArticle(inputArticle);

        // then
        assertThat(received).isEqualTo(expected);
    }

    @Test
    public void makeNewArticleFailThrowIOException() throws Exception {
        // given
        Article inputArticle = Article.builder()
                .attributes(Attributes.builder()
                        .title("Test Article")
                        .body("This is a test article")
                        .build())
                .build();

        IOException expected = new IOException();

        given(articleRepository.insert(inputArticle)).willThrow(expected);

        // when
        Throwable received = catchThrowable(() -> articleService.makeNewArticle(inputArticle));

        // then
        assertThat(received).isEqualTo(expected);
    }

    @Test
    public void changeArticleSuccessReturnChangedArticle() throws Exception {
        // given
        Article inputArticle = Article.builder()
                .id("1234")
                .attributes(Attributes.builder()
                        .body("This is a test article")
                        .title("Test article")
                        .build())
                .links(new Links("/articles/1234"))
                .build();
        Article expected = inputArticle;

        given(articleRepository.update(inputArticle)).willReturn(expected);

        // when
        Article received = articleService.changeArticle(inputArticle);

        // then
        assertThat(received).isEqualTo(expected);
    }

    @Test
    public void deleteArticleByIdSuccessReturnDeletedArticle() throws Exception {
        // given
        String id = "1234";
        Article expected = Article.builder()
                .id(id)
                .attributes(Attributes.builder().title("Test article").body("This is a test article").build())
                .links(new Links("/articles/" + id))
                .build();

        given(articleRepository.deleteById(id)).willReturn(expected);

        // when
        Article received = articleService.deleteArticleById(id);

        // then
        assertThat(received).isEqualTo(expected);
    }
}
