package ru.photorex.hw9.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorTo {

    private String fullName;

    @Override
    public String toString() {
        return fullName;
    }
}
