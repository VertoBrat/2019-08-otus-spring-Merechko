package ru.photorex.hw14.model.mapper;

public interface BaseMapper<E, T> {
    T toTo(E entity);
}
