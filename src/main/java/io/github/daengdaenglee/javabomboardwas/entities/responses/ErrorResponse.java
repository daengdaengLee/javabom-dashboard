package io.github.daengdaenglee.javabomboardwas.entities.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse<T> {
    List<T> errors;

    public ErrorResponse(List<T> errors) {
        this.errors = errors;
    }
}
