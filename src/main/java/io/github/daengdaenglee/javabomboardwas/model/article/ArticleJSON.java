package io.github.daengdaenglee.javabomboardwas.model.article;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleJSON {
    private String type;
    private String id;
    private AttributesJSON attributes;
    private LinksJSON links;

    @Builder
    public ArticleJSON(String type, String id, AttributesJSON attributes, LinksJSON links) {
        this.type = type;
        this.id = id;
        this.attributes = attributes;
        this.links = links;
    }
}
