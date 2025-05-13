package com.Bookstore.book_store.web.service.impl;

import com.Bookstore.book_store.exceptions.APIException;
import com.Bookstore.book_store.model.Book;
import com.Bookstore.book_store.model.Genre;
import com.Bookstore.book_store.web.payload.BookDto;
import com.Bookstore.book_store.web.payload.BookResponse;
import com.Bookstore.book_store.web.repository.BookRepository;
import com.Bookstore.book_store.web.repository.GenreRepository;
import com.Bookstore.book_store.web.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookResponse findAllBooks(int pageNumber, String sortBy, int pageSize, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Book> book = bookRepository.findAll(pageable);
        List<BookDto> bookListDto = book.getContent().stream()
                    .map(books -> {
                        BookDto bookDto = modelMapper.map(books, BookDto.class);
                        bookDto.setGenre(books.getGenre().getGenreName());
                        return bookDto;
                    })
                    .toList();

        return BookResponse.builder()
                .bookDTO(bookListDto)
                .totalPages(book.getTotalPages())
                .totalElements(book.getTotalElements())
                .lastPage(book.isLast())
                .pageSize(book.getSize())
                .pageNumber(book.getNumber())
                .build();
    }

    @Override
    public BookResponse searchByGenre(Long genreId ,int pageNumber, String sortBy, int pageSize,String sortOrder) {

        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new APIException("Genre not found"));

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Book> pageBook = bookRepository.findByGenre(genre,pageable);

        List<Book> books = pageBook.getContent();

        if(books.isEmpty()){
            throw new APIException(genre.getGenreName() + " category does not have any products");
        }

        List<BookDto> bookDTOs = books.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .toList();

        return BookResponse.builder()
                .bookDTO(bookDTOs)
                .totalPages(pageBook.getTotalPages())
                .totalElements(pageBook.getTotalElements())
                .lastPage(pageBook.isLast())
                .pageSize(pageBook.getSize())
                .pageNumber(pageBook.getNumber())
                .build();
    }

    @Transactional
    @Override
    public BookDto addNewBook(BookDto bookDto, Long genreId) {

         Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Genre not found"));

         List<Book> bookList = genre.getBooks();
         boolean isBookNoPresent = true;
         for (Book book1 : bookList) {
             if (book1.getTitle().equals(bookDto.getTitle())) {
                 isBookNoPresent = false;
                 break;
             }
         }
         if (isBookNoPresent) {
                Book book = modelMapper.map(bookDto, Book.class);
                book.setImage("default.png");
                book.setGenre(genre);
                Book bookSaved = bookRepository.save(book);
                return modelMapper.map(bookSaved, BookDto.class);
         }else {
             throw new APIException("Book "+ bookDto.getTitle() +" already exists!");
         }
    }

    @Override
    public BookDto updateBook(Long bookId, BookDto bookDto) {

        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new APIException("Book with id: "+ bookId +" not found!"));

        try {
            book.setTitle(bookDto.getTitle());
            book.setAuthor(bookDto.getAuthor());
            book.setDescription(bookDto.getDescription());
            book.setPrice(bookDto.getPrice());
            book.setImage(bookDto.getImage());
            book.setQuantity(bookDto.getQuantity());

            if( !book.getGenre().getGenreName().equals(bookDto.getGenre()) ){
                Genre genre = genreRepository.findByGenreName(bookDto.getGenre()).orElseThrow(() ->
                        new IllegalArgumentException("Genre not found"));
                book.setGenre(genre);
            }

           bookRepository.save(book);
           return  modelMapper.map(book, BookDto.class);
       }catch (Exception e){
           throw new RuntimeException(e);
       }
    }

    @Override
    public BookDto updateBookGenre(Long bookId, Long genreId) {
        Genre genre = genreRepository.findById(genreId).orElseThrow(() ->
                new IllegalArgumentException("Genre not found"));
        Book book = bookRepository.findById(bookId).orElseThrow(()->
                new IllegalArgumentException("Book with id: "+ bookId +" not found!"));

        book.setGenre(genre);
        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookDto.class);
    }

    @Override
    public BookResponse searchBookByKeyboard(String keyword, int pageNumber, String sortBy, int pageSize, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Book> pageBook = null;

        if(keyword == null){
            pageBook =  bookRepository.findAll(pageable);
        }else {
            pageBook = bookRepository.findByTitleLikeIgnoreCase('%'+ keyword +'%',pageable);
        }
        List<Book> book = pageBook.getContent();
        if (book.isEmpty()){
            throw new APIException(keyword + " category does not have any products");
        }
        List<BookDto> bookDTOs = book.stream().map((bookFromBd)->
                modelMapper.map(bookFromBd, BookDto.class))
                .toList();

        return BookResponse.builder()
                .bookDTO(bookDTOs)
                .pageNumber(pageable.getPageNumber())
                .totalElements(pageBook.getTotalElements())
                .totalPages(pageBook.getTotalPages())
                .lastPage(pageBook.isLast())
                .pageSize(pageBook.getSize())
                .pageNumber(pageable.getPageNumber())
                .build();
    }

    @Override
    public String deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
            return "Book deleted";
        }catch (Exception e){
            throw new APIException("Book not found");
        }
    }

}
