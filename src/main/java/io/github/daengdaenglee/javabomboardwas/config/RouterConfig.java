package io.github.daengdaenglee.javabomboardwas.config;

import io.github.daengdaenglee.javabomboardwas.constant.ArticleConstant;
import io.github.daengdaenglee.javabomboardwas.constant.CommonConstant;
import io.github.daengdaenglee.javabomboardwas.exception.article.ArticleNotFoundException;
import io.github.daengdaenglee.javabomboardwas.handler.article.ArticleHandler;
import io.github.daengdaenglee.javabomboardwas.handler.error.ErrorHandler;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleDeleteService;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleFindService;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleSaveService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
public class RouterConfig implements WebFluxConfigurer {
    private final String pathDefault = "";
    private final ArticleHandler articleHandler;
    private final ErrorHandler errorHandler;

    public RouterConfig(
            final ArticleFindService articleFindService,
            final ArticleSaveService articleSaveService,
            final ArticleDeleteService articleDeleteService
    ) {
        this.articleHandler = new ArticleHandler(
                articleFindService,
                articleSaveService,
                articleDeleteService
        );
        this.errorHandler = new ErrorHandler();
    }

    @Bean
    public RouterFunction<ServerResponse> rootRouter() {
        return RouterFunctions
                .route()
                .path(CommonConstant.BASE_PATH, builder -> builder
                    .add(articleRouter()))
                .filter(errorRouter())
                .build();
    }

    private RouterFunction<ServerResponse> articleRouter() {
        final String pathWithArticleId = "/{" + ArticleConstant.PATH_VAR_ARTICLE_ID + "}";

        return RouterFunctions
                .route()
                .path(ArticleConstant.BASE_PATH, builder -> builder
                        .GET(pathDefault, articleHandler::listAllArticles)
                        .GET(pathWithArticleId, articleHandler::readArticle)
                        .POST(pathDefault, articleHandler::createArticle)
                        .PUT(pathWithArticleId, articleHandler::updateArticle)
                        .DELETE(pathWithArticleId, articleHandler::deleteArticle))
                .build();
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> errorRouter() {
        return (request, next) -> next.handle(request)
                .onErrorResume(
                        ArticleNotFoundException.class,
                        exception -> errorHandler.handleArticleNotFoundException(request, exception))
                .onErrorResume(exception -> errorHandler.handleThrowable(request, exception));
    }
}
