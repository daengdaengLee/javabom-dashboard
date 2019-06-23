package io.github.daengdaenglee.javabomboardwas.unit.service.article;

import io.github.daengdaenglee.javabomboardwas.entity.article.Article;
import io.github.daengdaenglee.javabomboardwas.entity.article.Attribute;
import io.github.daengdaenglee.javabomboardwas.repository.ArticleRepository;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleDeleteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ArticleDeleteServiceTest {
    @MockBean
    private ArticleRepository articleRepository;

    private ArticleDeleteService articleDeleteService;

    private String givenIdString1;
    private Long givenIdLong1;
    private Article givenArticle1;

    @Before
    public void setUp() {
        givenIdString1 = "1";
        givenIdLong1 = 1L;
        givenArticle1 = Article.builder()
                .id(givenIdLong1)
                .attributes(Attribute.builder()
                        .title("Test")
                        .content("Test article")
                        .build())
                .build();

        articleDeleteService = new ArticleDeleteService(articleRepository);
    }

    @Test
    public void deleteById_withStringIdOfExistArticle_successWithMonoEmpty() {
        // given
        when(articleRepository.findById(givenIdLong1)).thenReturn(Optional.of(givenArticle1));

        // when
        Mono<Void> received = articleDeleteService.deleteById(givenIdString1);

        // then
        StepVerifier.create(received)
                .expectComplete()
                .verify();
    }

    @Test
    public void deleteById_withStringIdOfNotExistArticle_successWithMonoEmpty() {
        // given
        when(articleRepository.findById(givenIdLong1)).thenReturn(Optional.empty());

        // when
        Mono<Void> received = articleDeleteService.deleteById(givenIdString1);

        // then
        StepVerifier.create(received)
                .expectComplete()
                .verify();
    }

    @Test
    public void deleteById_withStringIdCantBoLong_failWithNumberFormatException() {
        // given
        String wrongId = "ABC";

        // when
        Mono<Void> received = articleDeleteService.deleteById(wrongId);

        // then
        StepVerifier.create(received)
                .expectError(NumberFormatException.class)
                .verify();
    }
}
