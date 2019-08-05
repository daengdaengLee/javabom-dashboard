package io.github.daengdaenglee.javabomboardwas.integration;

import io.github.daengdaenglee.javabomboardwas.constant.ArticleConstant;
import io.github.daengdaenglee.javabomboardwas.constant.CommonConstant;
import io.github.daengdaenglee.javabomboardwas.model.article.ArticleJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.AttributesJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.LinksJSON;
import io.github.daengdaenglee.javabomboardwas.model.container.DataContainer;
import io.github.daengdaenglee.javabomboardwas.repository.ArticleRepository;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleDeleteService;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleSaveService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class ArticleHandlerIntegrationTest {
    private static final String BASE_URL = CommonConstant.BASE_PATH + ArticleConstant.BASE_PATH;

    @Autowired
    private WebTestClient webClient;
    @Autowired
    private ArticleSaveService articleSaveService;
    @Autowired
    private ArticleRepository articleRepository;

    private ArticleJSON articleJSON1;
    private ArticleJSON articleJSON2;

    @Before
    public void setUp() {
        articleRepository.deleteAll();

        articleJSON1 = ArticleJSON.builder()
                .type(ArticleConstant.TYPE_ARTICLES)
                .id("1")
                .links(new LinksJSON(BASE_URL + "/1"))
                .attributes(AttributesJSON.builder()
                        .title("Article 1")
                        .content("article 1")
                        .build())
                .build();
        articleJSON2 = ArticleJSON.builder()
                .type(ArticleConstant.TYPE_ARTICLES)
                .id("2")
                .links(new LinksJSON(BASE_URL + "/2"))
                .attributes(AttributesJSON.builder()
                        .title("Article 2")
                        .content("article 2")
                        .build())
                .build();
    }

    @Test
    public void whenNoArticles_returnEmptyList() {
        webClient
                .get()
                .uri(BASE_URL)
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(new ParameterizedTypeReference<DataContainer<List<ArticleJSON>>>() {
                })
                .value(received -> {
                    List<ArticleJSON> receivedList = received.getData();

                    assertThat(receivedList.size()).isEqualTo(0);
                });
    }

    @Test
    public void whenExistingArticles_returnArticlesList() {
        articleJSON1 = articleSaveService.create(articleJSON1).block();
        articleJSON2 = articleSaveService.create(articleJSON2).block();

        webClient
                .get()
                .uri(BASE_URL)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<DataContainer<List<ArticleJSON>>>() {
                })
                .value(received -> {
                    List<ArticleJSON> receivedList = received.getData();

                    assertThat(receivedList.size()).isEqualTo(2);

                    assertArticleJSON(receivedList.get(0), articleJSON1);
                    assertArticleJSON(receivedList.get(1), articleJSON2);
                });
    }

    private void assertArticleJSON(final ArticleJSON received, final ArticleJSON expected) throws AssertionError {
        assertThat(received.getId()).isEqualTo(expected.getId());
        assertThat(received.getAttributes().getTitle())
                .isEqualTo(expected.getAttributes().getTitle());
        assertThat(received.getAttributes().getContent())
                .isEqualTo(expected.getAttributes().getContent());
        assertThat(received.getType()).isEqualTo(expected.getType());
        assertThat(received.getLinks().getSelf()).isEqualTo(expected.getLinks().getSelf());
    }
}
