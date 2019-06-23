package io.github.daengdaenglee.javabomboardwas.handler.error;

import io.github.daengdaenglee.javabomboardwas.exception.article.ArticleNotFoundException;
import io.github.daengdaenglee.javabomboardwas.model.container.ErrorContainer;
import io.github.daengdaenglee.javabomboardwas.model.error.Error;
import io.github.daengdaenglee.javabomboardwas.model.error.Source;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ErrorHandler {
    public Mono<ServerResponse> handleThrowable(
            final ServerRequest request,
            final Throwable exception) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        final Error error = Error.builder()
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(exception.toString())
                .source(new Source(request.path()))
                .build();

        final ErrorContainer body = new ErrorContainer(error);

        return ServerResponse.status(httpStatus).syncBody(body);
    }

    public Mono<ServerResponse> handleArticleNotFoundException(
            final ServerRequest request,
            final ArticleNotFoundException exception
    ) {
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        final Error error = Error.builder()
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(exception.getMessage())
                .source(new Source(exception.getSource()))
                .build();

        final ErrorContainer body = new ErrorContainer(error);

        return ServerResponse.status(httpStatus).syncBody(body);
    }
}
