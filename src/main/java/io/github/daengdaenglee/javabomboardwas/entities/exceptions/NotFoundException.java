package io.github.daengdaenglee.javabomboardwas.entities.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

public class NotFoundException extends BaseException {
    public NotFoundException(String detail, Source source) {
        super("404", "Not Found", detail, source);
    }

    @Override
    @JsonValue
    public String toString() {
        return super.toString();
    }
}
