package io.github.daengdaenglee.javabomboardwas.entities.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Error {
    String status;
    String title;
    String detail;
    Source source;
}
