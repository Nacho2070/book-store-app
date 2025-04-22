package com.Bookstore.book_store.web.repository;

import com.Bookstore.book_store.model.Book;
import com.Bookstore.book_store.model.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    Page<Book> findByGenre(Genre genre, Pageable pageable);
}
