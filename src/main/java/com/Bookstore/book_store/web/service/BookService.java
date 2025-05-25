package com.Bookstore.book_store.web.service;

import com.Bookstore.book_store.web.payload.BookDto;
import com.Bookstore.book_store.web.payload.BookResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookService {

    BookResponse searchByGenre(Long categoryId, int pageNumber, String sortBy, int pageSize,String sortOrder);

    BookResponse findAllBooks(int pageNumber, String sortBy, int pageSize, String sortOrder);

    BookDto addNewBook(BookDto bookDto, Long genreId);
    String deleteBook(Long id);

    BookDto updateBook(Long bookId, BookDto bookDto);

    BookDto updateBookGenre(Long bookId, Long genreId);

    BookResponse searchBookByKeyboard(String keyword,int pageNumber, String sortBy, int pageSize, String sortOrder);

    BookDto updateBookImage(@Valid Long bookId, MultipartFile image) throws IOException;
}
