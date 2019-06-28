package io.github.daengdaenglee.javabomboardwas.integration;

import io.github.daengdaenglee.javabomboardwas.constant.ArticleConstant;
import io.github.daengdaenglee.javabomboardwas.constant.CommonConstant;
import io.github.daengdaenglee.javabomboardwas.model.article.ArticleJSON;
import io.github.daengdaenglee.javabomboardwas.model.container.DataContainer;
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
}
