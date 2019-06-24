package io.github.daengdaenglee.javabomboardwas.unit.codec.article;

import io.github.daengdaenglee.javabomboardwas.codec.article.ArticleCodec;
import io.github.daengdaenglee.javabomboardwas.constant.ArticleConstant;
import io.github.daengdaenglee.javabomboardwas.constant.CommonConstant;
import io.github.daengdaenglee.javabomboardwas.entity.article.Article;
import io.github.daengdaenglee.javabomboardwas.entity.article.Attribute;
import io.github.daengdaenglee.javabomboardwas.model.article.ArticleJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.AttributesJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.LinksJSON;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BlockJUnit4ClassRunner.class)
public class ArticleCodecTest {
    private final Long articleIdLong = 1L;
    private final String articleIdStr = "1";
    private final String title = "Article 1";
    private final String content = "article 1";

    private ArticleCodec articleCodec;

    @Before
    public void setUp() {
        articleCodec = new ArticleCodec();
    }

    @Test
    public void fromEntityToModel_withValidArticle_successReturnArticleJSON() {
        //given
        Article article = Article.builder()
                .id(articleIdLong)
                .attributes(Attribute.builder()
                        .title(title)
                        .content(content)
                        .build())
                .build();

        // when
        ArticleJSON received = articleCodec.fromEntityToModel(article);

        // then
        assertThat(received.getId()).isEqualTo(articleIdStr);
        assertThat(received.getType()).isEqualTo(ArticleConstant.TYPE_ARTICLES);
        assertThat(received.getLinks().getSelf())
                .isEqualTo(CommonConstant.BASE_PATH + ArticleConstant.BASE_PATH + "/" + articleIdStr);
        assertThat(received.getAttributes().getTitle()).isEqualTo(title);
        assertThat(received.getAttributes().getContent()).isEqualTo(content);
    }

    @Test
    public void fromModelToEntity_withValidArticleJSON_successReturnArticle() {
        // given
        ArticleJSON articleJSON = ArticleJSON.builder()
                .id(articleIdStr)
                .type(ArticleConstant.TYPE_ARTICLES)
                .links(new LinksJSON(CommonConstant.BASE_PATH + ArticleConstant.BASE_PATH + "/" + articleIdStr))
                .attributes(AttributesJSON.builder()
                        .title(title)
                        .content(content)
                        .build())
                .build();

        // when
        Article received = articleCodec.fromModelToEntity(articleJSON);

        // then
        assertThat(received.getId()).isEqualTo(articleIdLong);
        assertThat(received.getAttributes().getTitle()).isEqualTo(title);
        assertThat(received.getAttributes().getContent()).isEqualTo(content);
    }
}
