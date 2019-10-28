package ru.photorex.server.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentTo {

    private String id;
    @NotBlank
    private String text;
    private Date dateTime;
    private String bookId;
}
