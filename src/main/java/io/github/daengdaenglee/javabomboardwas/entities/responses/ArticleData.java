package io.github.daengdaenglee.javabomboardwas.entities.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;
import lombok.Getter;

@Getter
public class ArticleData {
    private final Article data;

    public ArticleData(@JsonProperty("data") Article data) {
        this.data = data;
    }
}
