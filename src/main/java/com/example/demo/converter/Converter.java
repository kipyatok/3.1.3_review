package com.example.demo.converter;

import java.io.Serializable;
import java.util.List;

public interface Converter<E extends Serializable, P> {
    List<P> convert(List<E> entities);

    E convert(P bean);

    P convert(E entity);
}
