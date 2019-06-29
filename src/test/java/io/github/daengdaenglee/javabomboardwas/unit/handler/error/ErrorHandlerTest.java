package io.github.daengdaenglee.javabomboardwas.unit.handler.error;

import io.github.daengdaenglee.javabomboardwas.exception.article.ArticleNotFoundException;
import io.github.daengdaenglee.javabomboardwas.handler.error.ErrorHandler;
import io.github.daengdaenglee.javabomboardwas.model.container.ErrorContainer;
import io.github.daengdaenglee.javabomboardwas.model.error.Error;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ErrorHandlerTest {
    private static final String URI = "/";

    private WebTestClient.RequestHeadersSpec exchangeable;

    private ErrorHandler errorHandler;

    private Throwable throwable;
    private ArticleNotFoundException articleNotFoundException;

    private HttpStatus httpStatus;

    @Before
    public void setUp() {
        throwable = new Throwable("Test Throwable");
        articleNotFoundException = new ArticleNotFoundException("1");

        errorHandler = new ErrorHandler();
    }

    @Test
    public void handleThrowable_withThrowable_returnInternalServerError() {
        // given
        exchangeable = mockWebTestClientExchangeable(
                Throwable.class,
                throwable,
                ((request, e) -> errorHandler.handleThrowable(request, e))
        );
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        // when
        WebTestClient.ResponseSpec received = exchangeable.exchange();

        // then
        received
                .expectStatus()
                .isEqualTo(httpStatus)
                .expectBody(ErrorContainer.class)
                .value(errorContainer -> {
                    Error receivedError = errorContainer.getError();

                    assertThat(receivedError.getStatus()).isEqualTo(httpStatus.value());
                    assertThat(receivedError.getError()).isEqualTo(httpStatus.getReasonPhrase());
                    assertThat(receivedError.getMessage()).isEqualTo(throwable.toString());
                    assertThat(receivedError.getSource().getPointer()).isEqualTo(URI);
                });
    }

    @Test
    public void handleArticleNotFoundException_withArticleNotFoundException_returnNotFoundError() {
        // given
        httpStatus = HttpStatus.NOT_FOUND;
        exchangeable = mockWebTestClientExchangeable(
                ArticleNotFoundException.class,
                articleNotFoundException,
                (request, e) -> errorHandler.handleArticleNotFoundException(request, e)
        );

        // when
        WebTestClient.ResponseSpec received = exchangeable.exchange();

        // then
        received
                .expectStatus()
                .isEqualTo(httpStatus)
                .expectBody(ErrorContainer.class)
                .value(errorContainer -> {
                    Error receivedError = errorContainer.getError();

                    assertThat(receivedError.getStatus()).isEqualTo(httpStatus.value());
                    assertThat(receivedError.getError()).isEqualTo(httpStatus.getReasonPhrase());
                    assertThat(receivedError.getMessage()).isEqualTo(articleNotFoundException.getMessage());
                    assertThat(receivedError.getSource().getPointer()).isEqualTo(articleNotFoundException.getSource());
                });
    }

    private <T extends Throwable> WebTestClient.RequestHeadersSpec mockWebTestClientExchangeable(
            Class<T> clazz,
            T exception,
            BiFunction<ServerRequest, T, Mono<ServerResponse>> handler
    ) {
        RouterFunction<ServerResponse> routerFunction = RouterFunctions
                .route()
                .GET(URI, request -> Mono.error(exception))
                .filter((request, next) -> next
                        .handle(request)
                        .onErrorResume(
                                clazz,
                                ex -> handler.apply(request, ex)))
                .build();

        WebTestClient client = WebTestClient
                .bindToRouterFunction(routerFunction)
                .build();

        return client.get().uri(URI);
    }
}
