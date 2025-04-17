package com.walking.carpractice.converter;

import java.util.List;

public abstract class AbstractConverter<S, T> {
    public abstract T convert(S source);

    public List<T> convert(List<S> list) {
        return list.stream()
                .map(this::convert)
                .toList();
    }
}
