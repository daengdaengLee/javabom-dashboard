package io.github.daengdaenglee.javabomboardwas.service.article;

import io.github.daengdaenglee.javabomboardwas.entity.article.Article;
import io.github.daengdaenglee.javabomboardwas.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ArticleDeleteService {
    private final ArticleRepository articleRepository;

    public ArticleDeleteService(final ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Mono<Void> deleteById(final String id) {
        return Mono.justOrEmpty(id)
                .map(Long::valueOf)
                .map(articleRepository::findById)
                .flatMap(Mono::justOrEmpty)
                .map(Article::getId)
                .flatMap(longId -> {
                    articleRepository.deleteById(longId);
                    return Mono.empty();
                });
    }
}
