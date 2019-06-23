package io.github.daengdaenglee.javabomboardwas.model.article;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinksJSON {
    private String self;

    public LinksJSON(String self) {
        this.self = self;
    }
}
