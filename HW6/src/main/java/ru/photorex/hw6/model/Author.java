package ru.photorex.hw6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    private Long id;
    private String firstName;
    private String lastName;

    public Author(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return lastName + " " + firstName;
    }
}
