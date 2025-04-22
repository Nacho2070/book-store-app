package com.Bookstore.book_store.web.repository;

import com.Bookstore.book_store.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM cart c where c.user.email = ?1")
    Cart findByuserMail(String userMailAuth);
}
