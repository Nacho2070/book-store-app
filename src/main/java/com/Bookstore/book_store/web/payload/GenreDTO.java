package com.Bookstore.book_store.web.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {
    private Long genreId;
    private String genreName;
}
