package com.Bookstore.book_store.web.service;

import com.Bookstore.book_store.web.payload.CategoryResponse;
import com.Bookstore.book_store.web.payload.GenreDTO;
import jakarta.validation.Valid;

public interface GenreService {

    GenreDTO addNewGenre(GenreDTO genreName);

    GenreDTO updateGenre(Long genreId, @Valid GenreDTO genreDTO);

    CategoryResponse getAllGenres(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    GenreDTO deleteGenre(Long genreId);
}
