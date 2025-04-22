package com.Bookstore.book_store.web.service.impl;

import com.Bookstore.book_store.model.Book;
import com.Bookstore.book_store.model.Genre;
import com.Bookstore.book_store.web.payload.CategoryResponse;
import com.Bookstore.book_store.web.payload.GenreDTO;
import com.Bookstore.book_store.web.repository.BookRepository;
import com.Bookstore.book_store.web.repository.GenreRepository;
import com.Bookstore.book_store.web.service.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImp implements GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    @Override
    public CategoryResponse getAllGenres(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sort = sortOrder.equals("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable page = PageRequest.of(pageNumber, pageSize, sort);

        Page<Genre> genrePage = genreRepository.findAll(page);
        List<Genre> genreList = genrePage.getContent();
        genreList.forEach(System.out::println);

        List<GenreDTO> genreDTO = genreList.stream()
                .map(genre -> (
                        modelMapper.map(genre, GenreDTO.class)
                        )
        ).toList();
        return CategoryResponse.builder()
                .genres(genreDTO)
                .totalElements(genrePage.getTotalElements())
                .pageNumber(genrePage.getNumber())
                .pageSize(genrePage.getSize())
                .totalPages(genrePage.getTotalPages())
                .lastPage(genrePage.isLast())
                .build();
    }

    @Override
    public GenreDTO deleteGenre(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new RuntimeException("Genre not found"));

        genreRepository.delete(genre);

        return modelMapper.map(genre, GenreDTO.class);
    }

    @Override
    public GenreDTO addNewGenre(GenreDTO genreDTO) {

        Optional<Genre> genreOptional = genreRepository.findByGenreName(genreDTO.getGenreName());
        if(genreOptional.isPresent()) {
            throw new RuntimeException("Genre "+ genreDTO.getGenreName() +" already exists!");
        }

        Genre genre = modelMapper.map(genreDTO, Genre.class);

        Genre savedGenre = genreRepository.save(genre);
        log.info("new genre from db: {}", savedGenre.getGenreName());
        return modelMapper.map(savedGenre, GenreDTO.class);
    }

    @Override
    public GenreDTO updateGenre(Long genreId, GenreDTO genreDTO) {

        Genre genre = genreRepository.findById(genreId).orElseThrow(() ->
                new RuntimeException("Genre "+ genreDTO.getGenreName() +" not found"));

        if(genreDTO.getGenreName().equals(genre.getGenreName())){
            throw new RuntimeException("Genre "+ genreDTO.getGenreName() +" already exists!");
        }

        genre.setGenreName(genreDTO.getGenreName());
        genre = genreRepository.save(genre);

        return modelMapper.map(genre, GenreDTO.class);
    }

}
