package io.github.daengdaenglee.javabomboardwas.config.router;

import io.github.daengdaenglee.javabomboardwas.constant.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
public class RootRouterConfig {
    private static final String BASE_PATTERN = CommonConstant.BASE_PATH;

    private final ArticleRouterConfig articleRouterConfig;
    private final ErrorHandlerFilterConfig errorHandlerFilterConfig;

    public RootRouterConfig(
            ArticleRouterConfig articleRouterConfig,
            ErrorHandlerFilterConfig errorHandlerFilterConfig
    ) {
        this.articleRouterConfig = articleRouterConfig;
        this.errorHandlerFilterConfig = errorHandlerFilterConfig;
    }

    @Bean
    public RouterFunction<ServerResponse> rootRouter() {
        return RouterFunctions
                .route()
                .path(BASE_PATTERN, builder -> builder.add(articleRouterConfig.genArticleRouter()))
                .filter(errorHandlerFilterConfig.genDefaultErrorFilter())
                .filter(errorHandlerFilterConfig.genArticleErrorFilter())
                .build();
    }
}
