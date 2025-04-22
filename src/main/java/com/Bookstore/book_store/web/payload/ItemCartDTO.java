package com.Bookstore.book_store.web.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemCartDTO {
    private long bookId;
    private double quantity;
}
