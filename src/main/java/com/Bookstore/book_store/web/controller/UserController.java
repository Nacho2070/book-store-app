package com.Bookstore.book_store.web.controller;

import com.Bookstore.book_store.web.payload.UserDTO;
import com.Bookstore.book_store.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUser()
    {
        List<UserDTO> users = userService.getUser();
        return new ResponseEntity<>(users, HttpStatus.ACCEPTED);
    }

}
