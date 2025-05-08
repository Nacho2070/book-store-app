package com.Bookstore.book_store.web.service;

import com.Bookstore.book_store.web.payload.UserDTO;
import com.Bookstore.book_store.web.payload.UserLogInDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface UserAuthService {
    String logIn(UserLogInDTO userDTO);

    UserDTO logUp(@Valid UserDTO userDTO);
}
