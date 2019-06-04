package io.github.daengdaenglee.javabomboardwas.entities.articles;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Article {
    @Builder.Default private final String type = "articles";
    private final String id;
    private final Attributes attributes;
    private final Links links;
}
