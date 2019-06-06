package io.github.daengdaenglee.javabomboardwas.entities.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataResponse <T> {
    T data;
}
