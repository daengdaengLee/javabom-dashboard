package io.github.daengdaenglee.javabomboardwas.codec.article;

import io.github.daengdaenglee.javabomboardwas.constant.ArticleConstant;
import io.github.daengdaenglee.javabomboardwas.constant.CommonConstant;
import io.github.daengdaenglee.javabomboardwas.entity.article.Article;
import io.github.daengdaenglee.javabomboardwas.entity.article.Attribute;
import io.github.daengdaenglee.javabomboardwas.model.article.ArticleJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.AttributesJSON;
import io.github.daengdaenglee.javabomboardwas.model.article.LinksJSON;
import org.springframework.stereotype.Component;

@Component
public class ArticleCodec {
    public ArticleJSON fromEntityToModel(final Article article) {
        final String articleId = article.getId().toString();

        final String title = article.getAttributes().getTitle();
        final String content = article.getAttributes().getContent();
        final AttributesJSON attributes = AttributesJSON.builder()
                .title(title)
                .content(content)
                .build();

        final String self = CommonConstant.BASE_PATH + ArticleConstant.BASE_PATH + "/" + articleId;

        return ArticleJSON.builder()
                .id(articleId)
                .type(ArticleConstant.TYPE_ARTICLES)
                .links(new LinksJSON(self))
                .attributes(attributes)
                .build();
    }

    public Article fromModelToEntity(final ArticleJSON articleJSON) {
        final String id = articleJSON.getId();

        final String title = articleJSON.getAttributes().getTitle();
        final String content = articleJSON.getAttributes().getContent();
        final Attribute attributes = Attribute.builder()
                .title(title)
                .content(content)
                .build();

        return Article.builder()
                .id(id == null ? null : Long.valueOf(id))
                .attributes(attributes)
                .build();
    }
}
