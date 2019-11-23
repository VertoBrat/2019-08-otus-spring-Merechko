package ru.photorex.hw12.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegUser {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String fullName;
}
