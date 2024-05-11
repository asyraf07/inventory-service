package com.asyraf.inventory.dto.responses;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Data
@SuperBuilder
public class PageDto<T> {
    private Boolean failure;
    private String message;
    private Collection<T> data;
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;
}