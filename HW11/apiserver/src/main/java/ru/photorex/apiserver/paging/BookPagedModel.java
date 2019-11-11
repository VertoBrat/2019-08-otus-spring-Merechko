package ru.photorex.apiserver.paging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import ru.photorex.apiserver.to.BookTo;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BookPagedModel {

    private List<BookTo> content;
    private Pageable pageable;

    public BookPagedModel(){}

    public BookPagedModel(List<BookTo> list, Pageable pageable) {
        this.content = list;
        this.pageable = pageable;
    }

    @JsonProperty("books")
    public List<BookTo> content() {
        return content.stream().skip(pageable.getPageNumber() * (long)pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
    }

    @JsonProperty
    public PageParam page() {
        int size = Math.max(pageable.getPageSize(), 0);
        int totalElements = content.size();
        int totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 0;
        int number = pageable.getPageNumber();
        return new PageParam(size, totalElements, totalPages, number);
    }
}
