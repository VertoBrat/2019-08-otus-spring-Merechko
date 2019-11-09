package ru.photorex.apiserver.paging;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Pageable;
import ru.photorex.apiserver.to.BookTo;

import java.util.List;

public class BookPagedModel extends AbstractPagedModel<BookTo> {

    public BookPagedModel(List<BookTo> list, Pageable pageable) {
        super(list, pageable);
    }

    @Override
    @JsonProperty("books")
    public List<BookTo> content() {
        return super.content();
    }
}
