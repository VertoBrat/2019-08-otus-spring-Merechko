package ru.photorex.apiserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author{

    private String firstName;

    private String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
