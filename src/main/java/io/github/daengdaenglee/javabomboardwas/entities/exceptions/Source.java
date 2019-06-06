package io.github.daengdaenglee.javabomboardwas.entities.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.daengdaenglee.javabomboardwas.utilities.JsonUtility;

public class Source {
    public String pointer;

    public Source(String pointer) {
        this.pointer = pointer;
    }

    @Override
    @JsonValue
    public String toString() {
        return new StringBuffer()
                .append("{")
                .append("\"pointer\"")
                .append(":")
                .append(JsonUtility.fromNullableString(this.pointer))
                .append("}")
                .toString();
    }
}
