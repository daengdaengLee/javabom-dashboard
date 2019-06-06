package io.github.daengdaenglee.javabomboardwas.entities.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

public class InternalServerErrorException extends BaseException {
    public InternalServerErrorException(String detail, Source source) {
        super("500", "Internal Server Error", detail, source);
    }

    @Override
    @JsonValue
    public String toString() {
        return super.toString();
    }
}
