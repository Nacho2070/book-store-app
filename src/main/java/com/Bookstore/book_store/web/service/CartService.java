package com.Bookstore.book_store.web.service;

import com.Bookstore.book_store.web.payload.BookDto;
import com.Bookstore.book_store.web.payload.CartDTO;
import com.Bookstore.book_store.web.payload.ItemCartDTO;

import java.util.List;

public interface CartService {
    List<CartDTO> getCarts();

    String createOrUpdateCart(List<ItemCartDTO> cartItems);

    CartDTO addBookToCart(Long bookId, Integer quantity);
}
