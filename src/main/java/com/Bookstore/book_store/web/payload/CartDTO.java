package com.Bookstore.book_store.web.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private long cartId;
    private double totalPrice;
    private List<BookDto> bookDto = new ArrayList<>();
}
