package io.github.daengdaenglee.javabomboardwas.unit.service.article;

import io.github.daengdaenglee.javabomboardwas.codec.article.ArticleCodec;
import io.github.daengdaenglee.javabomboardwas.constant.ArticleConstant;
import io.github.daengdaenglee.javabomboardwas.constant.CommonConstant;
import io.github.daengdaenglee.javabomboardwas.entity.article.Article;
import io.github.daengdaenglee.javabomboardwas.entity.article.Attribute;
import io.github.daengdaenglee.javabomboardwas.model.article.ArticleJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.AttributesJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.LinksJSON;
import io.github.daengdaenglee.javabomboardwas.repository.ArticleRepository;
import io.github.daengdaenglee.javabomboardwas.service.article.ArticleSaveService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ArticleSaveServiceTest {
    @MockBean
    private ArticleRepository articleRepository;
    @MockBean
    private ArticleCodec articleCodec;

    private ArticleSaveService articleSaveService;

    private Article article1;
    private ArticleJSON articleJSON1;

    @Before
    public void setUp() {
        final String id1 = "1";
        final String title1 = "Test 1";
        final String content1 = "Test article 1";
        article1 = Article.builder()
                .id(Long.valueOf(id1))
                .attributes(Attribute.builder()
                        .title(title1)
                        .content(content1)
                        .build())
                .build();
        articleJSON1 = ArticleJSON.builder()
                .id(id1)
                .type(ArticleConstant.TYPE_ARTICLES)
                .links(new LinksJSON(CommonConstant.BASE_PATH + ArticleConstant.BASE_PATH + "/" + id1))
                .attributes(AttributesJSON.builder()
                        .title(title1)
                        .content(content1)
                        .build())
                .build();

        articleSaveService = new ArticleSaveService(articleRepository, articleCodec);
    }

    @Test
    public void create_withValidArticleJSON_successWithArticleJSONMono() {
        // given
        articleJSON1.setId(null);

        when(articleCodec.fromModelToEntity(any(ArticleJSON.class))).thenReturn(article1);
        when(articleRepository.save(any(Article.class))).thenReturn(article1);
        when(articleCodec.fromEntityToModel(article1)).thenReturn(articleJSON1);

        // when
        Mono<ArticleJSON> received = articleSaveService.create(articleJSON1);

        // then
        StepVerifier.create(received)
                .expectNext(articleJSON1)
                .expectComplete()
                .verify();
    }

    @Test
    public void update_withChangedTitle_successWithArticleJSONMono() {
        // given
        String articleIdStr = articleJSON1.getId();
        Long articleIdLong = article1.getId();

        String changedTitle = "Changed title";
        ArticleJSON inputArticleJSON = ArticleJSON.builder()
                .attributes(AttributesJSON.builder()
                        .title(changedTitle)
                        .build())
                .build();
        Article changedArticle = Article.builder()
                .id(articleIdLong)
                .attributes(Attribute.builder()
                        .title(changedTitle)
                        .content(article1.getAttributes().getContent())
                        .build())
                .build();
        articleJSON1.getAttributes().setTitle(changedTitle);

        when(articleRepository.findById(articleIdLong)).thenReturn(Optional.of(article1));
        when(articleRepository.save(any(Article.class))).thenReturn(changedArticle);
        when(articleCodec.fromEntityToModel(any(Article.class))).thenReturn(articleJSON1);

        // when
        Mono<ArticleJSON> received = articleSaveService.update(articleIdStr, inputArticleJSON);

        // then
        StepVerifier.create(received)
                .expectNext(articleJSON1)
                .expectComplete()
                .verify();
    }
}
