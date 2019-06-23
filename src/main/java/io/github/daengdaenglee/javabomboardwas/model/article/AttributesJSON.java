package io.github.daengdaenglee.javabomboardwas.model.article;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttributesJSON {
    private String title;
    private String content;

    @Builder
    public AttributesJSON(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
