package com.Bookstore.book_store.web.service;

import com.Bookstore.book_store.web.payload.BookDto;
import com.Bookstore.book_store.web.payload.BookResponse;

public interface BookService {

    BookResponse searchByGenre(Long categoryId, int pageNumber, String sortBy, int pageSize,String sortOrder);

    BookResponse findAllBooks(int pageNumber, String sortBy, int pageSize, String sortOrder);

    BookDto addNewBook(BookDto bookDto, Long genreId);
    String deleteBook(Long id);

    BookDto updateBook(Long bookId, BookDto bookDto);
}
