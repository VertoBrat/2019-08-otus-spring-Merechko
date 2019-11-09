package ru.photorex.apiserver.paging;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractPagedModel<T> {

    private List<T> content;
    private Pageable pageable;

    AbstractPagedModel(List<T> content, Pageable pageable) {
        this.content = content;
        this.pageable = pageable;
    }

    @JsonProperty
    public List<T> content() {
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
