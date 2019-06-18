package io.github.daengdaenglee.javabomboardwas.entity.article;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Link {
    private String self;

    @Builder
    public Link(String self) {
        this.self = self;
    }
}
