package io.github.daengdaenglee.javabomboardwas.entities.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataRequest<T> {
    T data;

    public DataRequest(T data) {
        this.data = data;
    }
}
