package io.github.daengdaenglee.javabomboardwas.unit.handler.article;

import io.github.daengdaenglee.javabomboardwas.constant.ArticleConstant;
import io.github.daengdaenglee.javabomboardwas.constant.CommonConstant;
import io.github.daengdaenglee.javabomboardwas.handler.article.ArticleHandler;
import io.github.daengdaenglee.javabomboardwas.model.article.ArticleJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.AttributesJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.LinksJSON;
import io.github.daengdaenglee.javabomboardwas.model.container.DataContainer;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleDeleteService;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleFindService;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleSaveService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ArticleHandlerTest {
    private ArticleHandler articleHandler;
    private WebTestClient client;

    @MockBean
    private ArticleSaveService articleSaveService;
    @MockBean
    private ArticleFindService articleFindService;
    @MockBean
    private ArticleDeleteService articleDeleteService;

    private ArticleJSON articleJSON1;
    private ArticleJSON articleJSON2;

    @Before
    public void setUp() {
        articleJSON1 = ArticleJSON.builder()
                .id("1")
                .type(ArticleConstant.TYPE_ARTICLES)
                .links(new LinksJSON(CommonConstant.BASE_PATH + ArticleConstant.BASE_PATH + "/1"))
                .attributes(AttributesJSON.builder()
                        .title("Article 1")
                        .content("article 1")
                        .build())
                .build();

        articleJSON2 = ArticleJSON.builder()
                .id("2")
                .type(ArticleConstant.TYPE_ARTICLES)
                .links(new LinksJSON(CommonConstant.BASE_PATH + ArticleConstant.BASE_PATH + "/2"))
                .attributes(AttributesJSON.builder()
                        .title("Article 2")
                        .content("article 2")
                        .build())
                .build();

        articleHandler = new ArticleHandler(
                articleFindService,
                articleSaveService,
                articleDeleteService
        );
    }

    @Test
    public void listAllArticles_whenArticlesExist_successReturnServerResponseContainingArticlesList() {
        // given
        client = mockWebTestClient(RequestPredicates.GET(ArticleConstant.BASE_PATH), articleHandler::listAllArticles);
        String uri = ArticleConstant.BASE_PATH;

        when(articleFindService.findAll()).thenReturn(Flux.just(articleJSON1, articleJSON2));

        // when
        WebTestClient.ResponseSpec received = client.get().uri(uri).exchange();

        // then
        received
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<DataContainer<List<ArticleJSON>>>() {
                })
                .value(dataContainer -> {
                    List<ArticleJSON> receivedArticles = dataContainer.getData();

                    ArticleJSON receivedArticle1 = receivedArticles.get(0);
                    ArticleJSON receivedArticle2 = receivedArticles.get(1);

                    assertArticleJSON(receivedArticle1, articleJSON1);
                    assertArticleJSON(receivedArticle2, articleJSON2);
                });
    }

    @Test
    public void readArticle_whenArticleExist_successReturnServerResponseContainingArticle() {
        // given
        client = mockWebTestClient(
                RequestPredicates
                        .GET(ArticleConstant.BASE_PATH + "/{" + ArticleConstant.PATH_VAR_ARTICLE_ID + "}"),
                articleHandler::readArticle);
        String uri = ArticleConstant.BASE_PATH + "/" + articleJSON1.getId();

        when(articleFindService.findById(articleJSON1.getId())).thenReturn(Mono.just(articleJSON1));

        // when
        WebTestClient.ResponseSpec received = client.get().uri(uri).exchange();

        // then
        received
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<DataContainer<ArticleJSON>>() {
                })
                .value(dataContainer -> {
                    ArticleJSON receivedArticle = dataContainer.getData();

                    assertArticleJSON(receivedArticle, articleJSON1);
                });
    }

    @Test
    public void createArticle_withArticleJSONToCreate_successReturnCreatedArticleJSON() {
        // given
        client = mockWebTestClient(RequestPredicates.POST(ArticleConstant.BASE_PATH), articleHandler::createArticle);
        String uri = ArticleConstant.BASE_PATH;

        ArticleJSON inputArticle = ArticleJSON.builder()
                .attributes(AttributesJSON.builder()
                        .title(articleJSON1.getAttributes().getTitle())
                        .content(articleJSON1.getAttributes().getContent())
                        .build())
                .build();
        when(articleSaveService.create(any(ArticleJSON.class))).thenReturn(Mono.just(articleJSON1));

        // when
        WebTestClient.ResponseSpec received = client
                .post()
                .uri(uri)
                .syncBody(new DataContainer<>(inputArticle))
                .exchange();

        // then
        received
                .expectStatus()
                .isCreated()
                .expectBody(new ParameterizedTypeReference<DataContainer<ArticleJSON>>() {
                })
                .value(dataContainer -> {
                    ArticleJSON receivedArticle = dataContainer.getData();

                    assertArticleJSON(receivedArticle, articleJSON1);
                });
    }

    @Test
    public void updateArticle_withExistArticleAndBothChangedTitleAndContent_successReturnChangedArticleJSON() {
        // given
        String articleId = articleJSON1.getId();

        client = mockWebTestClient(
                RequestPredicates
                        .PUT(ArticleConstant.BASE_PATH + "/{" + ArticleConstant.PATH_VAR_ARTICLE_ID + "}"),
                articleHandler::updateArticle
        );
        String uri = ArticleConstant.BASE_PATH + "/" + articleId;

        String changedTitle = articleJSON1.getAttributes().getTitle() + " changed";
        String changedContent = articleJSON1.getAttributes().getContent() + " changed";
        ArticleJSON inputArticle = ArticleJSON.builder()
                .attributes(AttributesJSON.builder()
                        .title(changedTitle)
                        .content(changedContent)
                        .build())
                .build();
        ArticleJSON changedArticle = ArticleJSON.builder()
                .id(articleId)
                .attributes(AttributesJSON.builder()
                        .title(changedTitle)
                        .content(changedContent)
                        .build())
                .type(ArticleConstant.TYPE_ARTICLES)
                .links(new LinksJSON(CommonConstant.BASE_PATH + ArticleConstant.BASE_PATH + "/" + articleId))
                .build();
        when(articleSaveService.update(eq(articleId), any(ArticleJSON.class))).thenReturn(Mono.just(changedArticle));

        // when
        WebTestClient.ResponseSpec received = client
                .put()
                .uri(uri)
                .syncBody(new DataContainer<>(inputArticle))
                .exchange();

        // then
        received
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<DataContainer<ArticleJSON>>() {
                })
                .value(dataContainer -> {
                    ArticleJSON receivedArticle = dataContainer.getData();

                    assertArticleJSON(receivedArticle, changedArticle);
                });
    }

    @Test
    public void deleteArticle_withExistArticleId_successReturnOk() {
        // given
        String articleId = articleJSON1.getId();

        client = mockWebTestClient(
                RequestPredicates
                        .DELETE(ArticleConstant.BASE_PATH + "/{" + ArticleConstant.PATH_VAR_ARTICLE_ID + "}"),
                articleHandler::deleteArticle
        );
        String uri = ArticleConstant.BASE_PATH + "/" + articleId;

        when(articleDeleteService.deleteById(articleId)).thenReturn(Mono.empty());

        // when
        WebTestClient.ResponseSpec received = client
                .delete()
                .uri(uri)
                .exchange();

        // then
        received
                .expectStatus()
                .isOk();
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

    private WebTestClient mockWebTestClient(
            RequestPredicate requestPredicate,
            HandlerFunction<ServerResponse> handlerFunction
    ) {
        RouterFunction<ServerResponse> routerFunction = RouterFunctions
                .route(requestPredicate, handlerFunction);

        return WebTestClient
                .bindToRouterFunction(routerFunction)
                .build();
    }
}
