package io.github.daengdaenglee.javabomboardwas.model.error;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Error {
    private int status;
    private String error;
    private String message;
    private Source source;

    @Builder
    public Error(
            int status,
            String error,
            String message,
            Source source
    ) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.source = source;
    }
}
