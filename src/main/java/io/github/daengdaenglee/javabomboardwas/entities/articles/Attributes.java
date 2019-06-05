package io.github.daengdaenglee.javabomboardwas.entities.articles;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Attributes {
    String title;
    String body;
}
