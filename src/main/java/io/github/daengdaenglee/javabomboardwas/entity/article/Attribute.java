package io.github.daengdaenglee.javabomboardwas.entity.article;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Embeddable
public class Attribute {
    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "content")
    private String content;

    @Builder
    public Attribute(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
