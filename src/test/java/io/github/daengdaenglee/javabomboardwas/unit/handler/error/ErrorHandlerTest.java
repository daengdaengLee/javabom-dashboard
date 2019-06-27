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
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ErrorHandlerTest {
    private final String pattern = "/";
    private final String uri = "/";

    private ErrorHandler errorHandler;
    private WebTestClient client;

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
        RouterFunction<ServerResponse> routerFunction = RouterFunctions
                .route()
                .GET(pattern, request -> Mono.error(throwable))
                .filter((request, next) -> next
                        .handle(request)
                        .onErrorResume(exception -> errorHandler.handleThrowable(request, exception)))
                .build();
        client = WebTestClient
                .bindToRouterFunction(routerFunction)
                .build();
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        // when
        WebTestClient.ResponseSpec received = client.get().uri(uri).exchange();

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
                   assertThat(receivedError.getSource().getPointer()).isEqualTo(uri);
                });
    }

    @Test
    public void handleArticleNotFoundException_withArticleNotFoundException_returnNotFoundError() {
        // given
        RouterFunction<ServerResponse> routerFunction = RouterFunctions
                .route()
                .GET(pattern, request -> Mono.error(articleNotFoundException))
                .filter((request, next) -> next
                        .handle(request)
                        .onErrorResume(
                                ArticleNotFoundException.class,
                                exception -> errorHandler.handleArticleNotFoundException(request, exception)))
                .build();
        client = WebTestClient
                .bindToRouterFunction(routerFunction)
                .build();
        httpStatus = HttpStatus.NOT_FOUND;

        // when
        WebTestClient.ResponseSpec received = client.get().uri(uri).exchange();

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
}
