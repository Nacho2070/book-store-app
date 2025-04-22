package com.Bookstore.book_store.web.controller;

import com.Bookstore.book_store.web.config.AppConstants;
import com.Bookstore.book_store.web.payload.CategoryResponse;
import com.Bookstore.book_store.web.payload.GenreDTO;
import com.Bookstore.book_store.web.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/public/genre")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<CategoryResponse> getAllGenres(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_GENRE_BY,required = false)String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder
    ){
        CategoryResponse genreDTO = genreService.getAllGenres(pageNumber,pageSize,sortBy,sortOrder);
      return new ResponseEntity<>(genreDTO,HttpStatus.OK);
    }

    @PostMapping("/newGenre")
    public ResponseEntity<GenreDTO> addNewGenre(@RequestBody GenreDTO genreDTO) {
        GenreDTO response = genreService.addNewGenre(genreDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable Long genreId,
            @Valid @RequestBody GenreDTO genreDTO)
    {
        GenreDTO response = genreService.updateGenre(genreId,genreDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<GenreDTO> deleteGenre(@PathVariable Long genreId)
    {
        GenreDTO response = genreService.deleteGenre(genreId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
