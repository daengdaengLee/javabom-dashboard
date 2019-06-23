package io.github.daengdaenglee.javabomboardwas.handler.article;

import io.github.daengdaenglee.javabomboardwas.model.container.DataContainer;
import io.github.daengdaenglee.javabomboardwas.model.article.ArticleJSON;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleDeleteService;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleFindService;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleSaveService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ArticleHandler {
    private final ArticleFindService articleFindService;
    private final ArticleSaveService articleSaveService;
    private final ArticleDeleteService articleDeleteService;

    public ArticleHandler(
            final ArticleFindService articleFindService,
            final ArticleSaveService articleSaveService,
            final ArticleDeleteService articleDeleteService
    ) {
        this.articleFindService = articleFindService;
        this.articleSaveService = articleSaveService;
        this.articleDeleteService = articleDeleteService;
    }

    public Mono<ServerResponse> listAllArticles(final ServerRequest request) {
        return Flux.defer(articleFindService::findAll)
                .collectList()
                .map(DataContainer::new)
                .flatMap(body -> {
                    final HttpStatus httpStatus = body.getData().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;

                    return ServerResponse.status(httpStatus).syncBody(body);
                });
    }

    public Mono<ServerResponse> readArticle(final ServerRequest request) {
        final String articleId = request.pathVariable("articleId");

        return Mono.justOrEmpty(articleId)
                .flatMap(articleFindService::findById)
                .map(DataContainer::new)
                .flatMap(body -> ServerResponse.ok().syncBody(body));
    }

    public Mono<ServerResponse> createArticle(final ServerRequest request) {
        return request.bodyToMono(
                new ParameterizedTypeReference<DataContainer<ArticleJSON>>() {
                })
                .map(DataContainer::getData)
                .flatMap(articleSaveService::create)
                .map(DataContainer::new)
                .flatMap(body -> ServerResponse.status(HttpStatus.CREATED).syncBody(body));
    }

    public Mono<ServerResponse> updateArticle(final ServerRequest request) {
        final String articleId = request.pathVariable("articleId");

        return request.bodyToMono(
                new ParameterizedTypeReference<DataContainer<ArticleJSON>>() {
                })
                .map(DataContainer::getData)
                .flatMap(articleJSON -> articleSaveService.update(articleId, articleJSON))
                .map(DataContainer::new)
                .flatMap(body -> ServerResponse.ok().syncBody(body));
    }

    public Mono<ServerResponse> deleteArticle(final ServerRequest request) {
        final String articleId = request.pathVariable("articleId");

        return Mono.justOrEmpty(articleId)
                .flatMap(articleDeleteService::deleteById)
                .flatMap(m -> ServerResponse.ok().build());
    }
}
