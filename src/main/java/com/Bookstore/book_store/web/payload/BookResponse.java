package com.Bookstore.book_store.web.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class BookResponse{
    private List<BookDto> bookDTO;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}