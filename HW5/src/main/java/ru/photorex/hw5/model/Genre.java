package ru.photorex.hw5.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    private Long id;
    private String name;

    @Override
    public String toString() {
        return getName();
    }
}
