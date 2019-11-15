package ru.photorex.hw12.to.mapper;

import java.util.List;
import java.util.Set;

public interface BaseMapper<E, T> {

    T toTo(E entity);

    E toEntity(T to);

    List<T> toListTo(List<E> entityList);

    List<E> toList(List<T> toList);

    Set<T> toSetTo(Set<E> entitySet);

    Set<E> toSet(Set<T> toSet);
}
