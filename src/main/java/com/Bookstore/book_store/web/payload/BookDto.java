package com.Bookstore.book_store.web.payload;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long bookId;

    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, message = "Title must be at least 3 characters long")
    private String title;

    private String author;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;

    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private Double price;

    private String image;
    private String genre;
    @Min(value = 0, message = "Quantity can't be negative")
    private Integer quantity;
}