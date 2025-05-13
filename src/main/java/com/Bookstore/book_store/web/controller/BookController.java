package com.Bookstore.book_store.web.controller;

import com.Bookstore.book_store.web.config.AppConstants;
import com.Bookstore.book_store.web.payload.BookDto;
import com.Bookstore.book_store.web.payload.BookResponse;
import com.Bookstore.book_store.web.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<BookResponse> findAllBook(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER ,required = false) int pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BOOK_BY  ,required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false)String sortOrder
    ) {
        BookResponse bookResponse = bookService.findAllBooks(pageNumber,sortBy,pageSize,sortOrder);
        return ResponseEntity.ok(bookResponse);
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<BookResponse> searchAllByGenre(
            @PathVariable Long genreId,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER ,required = false) int pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BOOK_BY ,required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    )
    {
        BookResponse bookResponse = bookService.searchByGenre(genreId,pageNumber,sortBy,pageSize,sortOrder);
        return ResponseEntity.ok(bookResponse);
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(
            @RequestBody BookDto bookDto,
            @RequestParam(name = "genreId", required = false) Long genreId
    )
    {
        BookDto bookDTO = bookService.addNewBook(bookDto,genreId);
        return ResponseEntity.ok(bookDTO);
    }
    @GetMapping("/keyword")
    public ResponseEntity<BookResponse> getBookByKeyword(
            @RequestParam (name = "keyword",required = false)String keyword,
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER ,required = false) int pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BOOK_BY ,required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
    )
    {
        BookResponse updatedBook = bookService.searchBookByKeyboard(keyword,pageNumber,sortBy,pageSize,sortOrder);
        return new ResponseEntity<>(updatedBook,HttpStatus.OK);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable Long bookId,
            @Valid @RequestBody BookDto bookDto)
    {
        BookDto updatedBook = bookService.updateBook(bookId,bookDto);
        return new ResponseEntity<>(updatedBook,HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        String response = bookService.deleteBook(bookId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
 
    @PatchMapping("/{bookId}/genre/{genreId}")
    public ResponseEntity<BookDto> updateBookGenre(@PathVariable @Valid Long bookId,
                                                   @PathVariable @Valid Long genreId)
    {
        BookDto bookGenreUpdated = bookService.updateBookGenre(bookId,genreId);
        return new ResponseEntity<>(bookGenreUpdated, HttpStatus.OK);
    }

}
