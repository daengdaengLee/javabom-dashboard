package io.github.daengdaenglee.javabomboardwas.entities.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Attributes {
    String title;
    String body;

    public Attributes(
            @JsonProperty("title") String title,
            @JsonProperty("body") String body
    ) {
        this.title = title;
        this.body = body;
    }
}
