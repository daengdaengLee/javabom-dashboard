package io.github.daengdaenglee.javabomboardwas.entities.articles;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Article {
    @Builder.Default String type = "articles";
    String id;
    Attributes attributes;
    Links links;
}
