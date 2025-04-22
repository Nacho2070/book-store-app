package com.Bookstore.book_store.web.repository;

import com.Bookstore.book_store.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByGenreName(String name);
}
