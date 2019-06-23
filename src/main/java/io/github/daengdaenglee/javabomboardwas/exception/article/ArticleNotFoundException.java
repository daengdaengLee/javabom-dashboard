package io.github.daengdaenglee.javabomboardwas.exception.article;

import lombok.Getter;

@Getter
public class ArticleNotFoundException extends RuntimeException {
    private final String articleId;
    private final String source;

    public ArticleNotFoundException(final String articleId) {
        super("The article of id " + articleId + " is not exist");
        this.articleId = articleId;
        this.source = "/article/id/" + articleId;
    }
}
