package com.Bookstore.book_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "genre")
@Getter
@Setter
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long genreId;

    @NotBlank
    @Size(min = 5, max = 50, message = "Genre must contain atleast 5 characters ")
    private String genreName;

    @OneToMany(mappedBy = "genre",cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public Genre(String genreName) {
        this.genreName = genreName;
    }
}
