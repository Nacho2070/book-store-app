package com.Bookstore.book_store.web.config;

import com.Bookstore.book_store.model.Genre;
import com.Bookstore.book_store.model.Role;
import com.Bookstore.book_store.web.payload.AppRole;
import com.Bookstore.book_store.web.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final GenreRepository genreRepository;
    private final UserRolRepository userRolRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading Genres and Role from database...");

        if (genreRepository.findAll().isEmpty() ){
            genreRepository.saveAll(
                    List.of(
                            new Genre("Ficción"),
                            new Genre("Ciencia Ficción"),
                            new Genre("Fantasía"),
                            new Genre("Terror"),
                            new Genre("Romance"),
                            new Genre("Misterio"),
                            new Genre("Biografía"),
                            new Genre("Historia"),
                            new Genre("Unknown")
                    )
            );
        }
        if (userRolRepository.findAll().isEmpty() ){
            userRolRepository.saveAll(
                    List.of(
                            new Role(AppRole.USER),
                            new Role(AppRole.ADMIN)
                    )
            );
        }
        log.info("Data loaded.");
    }
}
