package io.github.daengdaenglee.javabomboardwas.entities.articles;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Links {
    String self;

    public Links(@JsonProperty("self") String self) {
        this.self = self;
    }
}
