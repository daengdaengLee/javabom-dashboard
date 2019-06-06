package io.github.daengdaenglee.javabomboardwas.entities.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse<T> {
    List<T> errors;
}
