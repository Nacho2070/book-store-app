package com.Bookstore.book_store.web.service;

import com.Bookstore.book_store.web.payload.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getUser();
}
