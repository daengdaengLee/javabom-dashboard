package io.github.daengdaenglee.javabomboardwas.entities.articles;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Builder.Default
    String type = "articles";
    String id;
    Attributes attributes;
    Links links;
}
