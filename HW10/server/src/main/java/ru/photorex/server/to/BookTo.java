package ru.photorex.server.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "books")
public class BookTo extends RepresentationModel<BookTo> {

    private String id;
    private String title;
    private Set<GenreTo> genres;
    private Set<AuthorTo> authors;
}
