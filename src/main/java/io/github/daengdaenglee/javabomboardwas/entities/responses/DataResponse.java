package io.github.daengdaenglee.javabomboardwas.entities.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataResponse <T> {
    T data;

    public DataResponse (T data) {
        this.data = data;
    }
}
