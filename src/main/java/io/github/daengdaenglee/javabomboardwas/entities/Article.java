package io.github.daengdaenglee.javabomboardwas.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {
    public String id;
    public String title;
    public String body;

    @JsonCreator
    public Article(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title,
            @JsonProperty("body") String body
    ) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
