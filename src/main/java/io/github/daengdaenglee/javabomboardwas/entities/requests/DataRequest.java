package io.github.daengdaenglee.javabomboardwas.entities.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataRequest<T> {
    T data;

    public DataRequest(@JsonProperty("data") T data) {
        this.data = data;
    }
}
