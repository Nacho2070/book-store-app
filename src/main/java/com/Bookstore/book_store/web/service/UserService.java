package com.Bookstore.book_store.web.service;

import com.Bookstore.book_store.web.payload.UserDTO;
import jakarta.validation.Valid;

public interface UserService {
    String logIn(UserDTO userDTO);

    UserDTO logUp(@Valid UserDTO userDTO);
}
