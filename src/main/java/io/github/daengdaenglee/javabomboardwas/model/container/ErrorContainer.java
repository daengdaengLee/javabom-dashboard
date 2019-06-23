package io.github.daengdaenglee.javabomboardwas.model.container;

import io.github.daengdaenglee.javabomboardwas.model.error.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorContainer {
    private Error error;

    public static ErrorContainer from(Error error) {
        return new ErrorContainer(error);
    }
}
