package io.github.daengdaenglee.javabomboardwas.entities.articles;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Attributes {
    private final String title;
    private final String body;
}
