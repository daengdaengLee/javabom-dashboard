package io.github.daengdaenglee.javabomboardwas.config.router;

import io.github.daengdaenglee.javabomboardwas.constant.ArticleConstant;
import io.github.daengdaenglee.javabomboardwas.handler.article.ArticleHandler;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleDeleteService;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleFindService;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleSaveService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class ArticleRouterConfig {
    private static final String BASE_PATTERN = ArticleConstant.BASE_PATH;
    private static final String ARTICLE_ID_PATTERN = "/{" + ArticleConstant.PATH_VAR_ARTICLE_ID + "}";

    private final ArticleHandler articleHandler;

    public ArticleRouterConfig(
            final ArticleFindService articleFindService,
            final ArticleSaveService articleSaveService,
            final ArticleDeleteService articleDeleteService
    ) {
        articleHandler = new ArticleHandler(
                articleFindService,
                articleSaveService,
                articleDeleteService
        );
    }

    public RouterFunction<ServerResponse> genArticleRouter() {
        return RouterFunctions
                .route()
                .path(BASE_PATTERN, builder -> builder
                    .GET("", articleHandler::listAllArticles)
                    .GET(ARTICLE_ID_PATTERN, articleHandler::readArticle)
                    .POST("", articleHandler::createArticle)
                    .PUT(ARTICLE_ID_PATTERN, articleHandler::updateArticle)
                    .DELETE(ARTICLE_ID_PATTERN, articleHandler::deleteArticle))
                .build();
    }
}
