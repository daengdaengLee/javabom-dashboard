package io.github.daengdaenglee.javabomboardwas.service.article;

import io.github.daengdaenglee.javabomboardwas.codec.article.ArticleCodec;
import io.github.daengdaenglee.javabomboardwas.exception.article.ArticleNotFoundException;
import io.github.daengdaenglee.javabomboardwas.model.article.ArticleJSON;
import io.github.daengdaenglee.javabomboardwas.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ArticleFindService {
    private final ArticleRepository articleRepository;
    private final ArticleCodec articleCodec;

    public ArticleFindService(
            final ArticleRepository articleRepository,
            final ArticleCodec articleCodec
    ) {
        this.articleRepository = articleRepository;
        this.articleCodec = articleCodec;
    }

    public Flux<ArticleJSON> findAll() {
        return Flux.fromIterable(articleRepository.findAll())
                .map(articleCodec::fromEntityToModel);
    }

    public Mono<ArticleJSON> findById(final String id) {
        return Mono.justOrEmpty(id)
                .map(Long::valueOf)
                .map(articleRepository::findById)
                .flatMap(Mono::justOrEmpty)
                .map(articleCodec::fromEntityToModel)
                .switchIfEmpty(Mono.error(new ArticleNotFoundException(id)));
    }
}
