package io.github.daengdaenglee.javabomboardwas.config.router;

import io.github.daengdaenglee.javabomboardwas.exception.article.ArticleNotFoundException;
import io.github.daengdaenglee.javabomboardwas.handler.error.ErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class ErrorHandlerFilterConfig {
    private final ErrorHandler errorHandler;

    public ErrorHandlerFilterConfig() {
        errorHandler = new ErrorHandler();
    }

    public HandlerFilterFunction<ServerResponse, ServerResponse> genDefaultErrorFilter() {
        return (request, next) -> next
                .handle(request)
                .onErrorResume(exception -> errorHandler.handleThrowable(request, exception));
    }

    public HandlerFilterFunction<ServerResponse, ServerResponse> genArticleErrorFilter() {
        return (request, next) -> next
                .handle(request)
                .onErrorResume(
                        ArticleNotFoundException.class,
                        exception -> errorHandler.handleArticleNotFoundException(request, exception));
    }
}
