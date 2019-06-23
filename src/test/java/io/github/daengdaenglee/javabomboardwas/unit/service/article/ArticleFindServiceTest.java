package io.github.daengdaenglee.javabomboardwas.unit.service.article;

import io.github.daengdaenglee.javabomboardwas.codec.article.ArticleCodec;
import io.github.daengdaenglee.javabomboardwas.constant.ArticleConstant;
import io.github.daengdaenglee.javabomboardwas.constant.CommonConstant;
import io.github.daengdaenglee.javabomboardwas.entity.article.Article;
import io.github.daengdaenglee.javabomboardwas.entity.article.Attribute;
import io.github.daengdaenglee.javabomboardwas.exception.article.ArticleNotFoundException;
import io.github.daengdaenglee.javabomboardwas.model.article.ArticleJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.AttributesJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.LinksJSON;
import io.github.daengdaenglee.javabomboardwas.repository.ArticleRepository;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleFindService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ArticleFindServiceTest {
    @MockBean
    private ArticleRepository articleRepository;
    @MockBean
    private ArticleCodec articleCodec;

    private ArticleFindService articleFindService;

    private Article savedArticleEntity1;
    private Article savedArticleEntity2;
    private ArticleJSON tranformedArticle1;
    private ArticleJSON tranformedArticle2;

    @Before
    public void setUp() {
        final String title1 = "Test 1";
        final String content1 = "Test article 1";
        savedArticleEntity1 = Article.builder()
                .id(1L)
                .attributes(Attribute.builder()
                        .title(title1)
                        .content(content1)
                        .build())
                .build();
        tranformedArticle1 = ArticleJSON.builder()
                .id("1")
                .attributes(AttributesJSON.builder()
                        .title(title1)
                        .content(content1)
                        .build())
                .type(ArticleConstant.TYPE_ARTICLES)
                .links(new LinksJSON(CommonConstant.BASE_PATH + ArticleConstant.BASE_PATH + "/1"))
                .build();

        final String title2 = "Test 2";
        final String content2 = "Test article 2";
        savedArticleEntity2 = Article.builder()
                .id(2L)
                .attributes(Attribute.builder()
                        .title(title2)
                        .content(content2)
                        .build())
                .build();
        tranformedArticle2 = ArticleJSON.builder()
                .id("2")
                .attributes(AttributesJSON.builder()
                        .title(title2)
                        .content(content2)
                        .build())
                .type(ArticleConstant.TYPE_ARTICLES)
                .links(new LinksJSON(CommonConstant.BASE_PATH + ArticleConstant.BASE_PATH + "/1"))
                .build();

        articleFindService = new ArticleFindService(articleRepository, articleCodec);
    }

    @Test
    public void findAll_whenExistArticles_successWithArticleJSONFlux() {
        // given
        when(articleRepository.findAll()).thenReturn(Arrays.asList(savedArticleEntity1, savedArticleEntity2));
        when(articleCodec.fromEntityToModel(savedArticleEntity1)).thenReturn(tranformedArticle1);
        when(articleCodec.fromEntityToModel(savedArticleEntity2)).thenReturn(tranformedArticle2);

        // when
        Flux<ArticleJSON> received = articleFindService.findAll();

        // then
        StepVerifier.create(received)
                .expectNext(tranformedArticle1)
                .expectNext(tranformedArticle2)
                .expectComplete()
                .verify();
    }

    @Test
    public void findAll_whenNoExistArticle_successWithEmptyFlux() {
        // given
        when(articleRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        Flux<ArticleJSON> received = articleFindService.findAll();

        // then
        StepVerifier.create(received)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById_withExistArticleId_successWithArticleJSONMono() {
        // given
        when(articleRepository.findById(savedArticleEntity1.getId())).thenReturn(Optional.of(savedArticleEntity1));
        when(articleCodec.fromEntityToModel(savedArticleEntity1)).thenReturn(tranformedArticle1);

        // when
        Mono<ArticleJSON> received = articleFindService.findById(savedArticleEntity1.getId().toString());

        // then
        StepVerifier.create(received)
                .expectNext(tranformedArticle1)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById_withNotExistArticleId_failWithArticleNotFoundException() {
        // given
        String notExistArticleId = "3";
        when(articleRepository.findById(Long.valueOf(notExistArticleId))).thenReturn(Optional.empty());

        // when
        Mono<ArticleJSON> received = articleFindService.findById(notExistArticleId);

        // then
        StepVerifier.create(received)
                .expectErrorMatches(exception -> {
                    if (!(exception instanceof ArticleNotFoundException)) return false;
                    return ((ArticleNotFoundException) exception).getArticleId().equals(notExistArticleId);
                })
                .verify();
    }

    @Test
    public void findById_withIdCantBeLong_failWithNumberFormatException() {
        // given
        String wrongId = "ABC";

        // when
        Mono<ArticleJSON> received = articleFindService.findById(wrongId);

        // then
        StepVerifier.create(received)
                .expectError(NumberFormatException.class)
                .verify();
    }
}
