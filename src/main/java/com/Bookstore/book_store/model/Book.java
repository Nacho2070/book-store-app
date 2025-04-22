package com.Bookstore.book_store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NotBlank
    @Size(min = 3, message = "Title must contain at least 3 characters")
    private String title;
    private String author;

    @NotBlank
    @Size(min = 6, message = "Description must contain at least 6 characters")
    private String description;
    private Double price;
    private String image;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL)
    private List<ItemCart> itemCart = new ArrayList<>();

    @ManyToMany(mappedBy = "favoriteBooks")
    private List<User> users = new ArrayList<>();


}
