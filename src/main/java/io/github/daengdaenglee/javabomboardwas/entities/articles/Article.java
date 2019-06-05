package io.github.daengdaenglee.javabomboardwas.entities.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Article {
    @Builder.Default
    String type = "articles";
    String id;
    Attributes attributes;
    Links links;

    public Article(
            @JsonProperty("type") String type,
            @JsonProperty("id") String id,
            @JsonProperty("attributes") Attributes attributes,
            @JsonProperty("links") Links links
    ) {
        this.type = type;
        this.id = id;
        this.attributes = attributes;
        this.links = links;
    }
}
