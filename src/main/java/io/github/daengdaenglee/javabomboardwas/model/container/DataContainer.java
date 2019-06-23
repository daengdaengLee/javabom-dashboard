package io.github.daengdaenglee.javabomboardwas.model.container;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataContainer<T> {
    private T data;

    public DataContainer(T data) {
        this.data = data;
    }
}
