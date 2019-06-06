package io.github.daengdaenglee.javabomboardwas.utilities;

public class JsonUtility {
    public static String fromNullableString(String value) {
        return value == null
                ? "null"
                : new StringBuffer().append("\"").append(value).append("\"").toString();
    }
}
