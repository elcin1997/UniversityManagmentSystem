package org.example.universitymanagementsystem.shared;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;

    public PageResponse(List<T> content, int totalPages, long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}

