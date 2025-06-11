package com.snailcatch.server.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginationResponse<T> {
    private List<T> content;
    private int totalPages;
    private int currentPage;
    private long totalElements;

    public static <T> PaginationResponse<T> of(Page<T> page) {
        return new PaginationResponse<>(
                page.getContent(),
                page.getTotalPages(),
                page.getNumber(),
                page.getTotalElements()
        );
    }
}
