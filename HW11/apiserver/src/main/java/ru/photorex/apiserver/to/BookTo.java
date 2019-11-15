package ru.photorex.apiserver.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTo {

    private String id;
    @NotBlank
    private String title;

    private String content;

    @Size(min = 1)
    private Set<GenreTo> genres = new HashSet<>();

    @Size(min = 1)
    private Set<AuthorTo> authors = new HashSet<>();
    private List<CommentTo> comments = new ArrayList<>();
}
