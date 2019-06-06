package io.github.daengdaenglee.javabomboardwas.entities.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.daengdaenglee.javabomboardwas.utilities.JsonUtility;

public class BaseException extends Exception {
    public String status;
    public String title;
    public String detail;
    public Source source;

    public BaseException(
            String status,
            String title,
            String detail,
            Source source
    ) {
        super(title);

        this.status = status;
        this.title = title;
        this.detail = detail;
        this.source = source;
    }

    @Override
    @JsonValue
    public String toString() {
        String status = new StringBuffer()
                .append("\"status\"")
                .append(":")
                .append(JsonUtility.fromNullableString(this.status))
                .toString();

        String title = new StringBuffer()
                .append("\"title\"")
                .append(":")
                .append(JsonUtility.fromNullableString(this.title))
                .toString();

        String detail = new StringBuffer()
                .append("\"detail\"")
                .append(":")
                .append(JsonUtility.fromNullableString(this.detail))
                .toString();

        String source = new StringBuffer()
                .append("\"source\"")
                .append(":")
                .append(this.source.toString())
                .toString();

        return new StringBuffer()
                .append("{")
                .append(status)
                .append(",")
                .append(title)
                .append(",")
                .append(detail)
                .append(",")
                .append(source)
                .append("}")
                .toString();
    }

    public static class Builder {
        String status;
        String title;
        String detail;
        Source source;

        public Builder() {
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder source(Source source) {
            this.source = source;
            return this;
        }

        public BaseException build() {
            return new BaseException(
                    this.status,
                    this.title,
                    this.detail,
                    this.source
            );
        }
    }
}
