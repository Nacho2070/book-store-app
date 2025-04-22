package com.Bookstore.book_store.web.repository;

import com.Bookstore.book_store.model.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemCartRepository extends JpaRepository<ItemCart,Long> {

    @Query("SELECT ic from item_cart ic where ic.cart.cartId = ?1 and ic.book.bookId = ?2")
    ItemCart findItemCartBycartIdAndBookId(Long cartId, Long bookId);
}
