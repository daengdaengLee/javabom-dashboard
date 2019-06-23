package io.github.daengdaenglee.javabomboardwas.service.article;

import io.github.daengdaenglee.javabomboardwas.codec.article.ArticleCodec;
import io.github.daengdaenglee.javabomboardwas.entity.article.Article;
import io.github.daengdaenglee.javabomboardwas.entity.article.Attribute;
import io.github.daengdaenglee.javabomboardwas.exception.article.ArticleNotFoundException;
import io.github.daengdaenglee.javabomboardwas.model.article.ArticleJSON;
import io.github.daengdaenglee.javabomboardwas.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ArticleSaveService {
    private final ArticleRepository articleRepository;
    private final ArticleCodec articleCodec;

    public ArticleSaveService(
            final ArticleRepository articleRepository,
            final ArticleCodec articleCodec
    ) {
        this.articleRepository = articleRepository;
        this.articleCodec = articleCodec;
    }

    public Mono<ArticleJSON> create(final ArticleJSON articleJSON) {
        return Mono.justOrEmpty(articleJSON)
                .map(json -> {
                    json.setId(null);
                    return json;
                })
                .map(articleCodec::fromModelToEntity)
                .map(articleRepository::save)
                .map(articleCodec::fromEntityToModel);
    }

    public Mono<ArticleJSON> update(final String articleId, final ArticleJSON articleJSON) {
        return Mono.justOrEmpty(articleId)
                .map(Long::valueOf)
                .map(articleRepository::findById)
                .flatMap(Mono::justOrEmpty)
                .map(originalArticle -> {
                    final String originalTitle = originalArticle.getAttributes().getTitle();
                    final String inputTitle = articleJSON.getAttributes().getTitle();
                    final String title = inputTitle == null ? originalTitle : inputTitle;

                    final String originalContent = originalArticle.getAttributes().getContent();
                    final String inputContent = articleJSON.getAttributes().getContent();
                    final String content = inputContent == null ? originalContent : inputContent;

                    return Article.builder()
                            .id(originalArticle.getId())
                            .attributes(Attribute.builder()
                                    .title(title)
                                    .content(content)
                                    .build())
                            .build();
                })
                .map(articleRepository::save)
                .map(articleCodec::fromEntityToModel)
                .switchIfEmpty(Mono.error(new ArticleNotFoundException(articleId)));
    }
}
