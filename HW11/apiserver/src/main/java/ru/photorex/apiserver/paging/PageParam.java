package ru.photorex.apiserver.paging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PageParam {

    private final int size;
    private final int totalElements;
    private final int totalPages;
    private final int number;
}
