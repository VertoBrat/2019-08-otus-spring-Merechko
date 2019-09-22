package ru.photorex.hw5.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"id"})
public class Genre {

    private Long id;
    private String name;

    public Genre() {
    }

    public Genre(Long id) {
        this(id, null);
    }

    public Genre(String name) {
        this(null, name);
    }

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }



    @Override
    public String toString() {
        return getName();
    }
}
