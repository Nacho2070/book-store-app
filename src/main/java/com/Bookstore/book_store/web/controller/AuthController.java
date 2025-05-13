package com.Bookstore.book_store.web.controller;

import com.Bookstore.book_store.web.payload.RegisterUserDTO;
import com.Bookstore.book_store.web.payload.UserDTO;
import com.Bookstore.book_store.web.payload.UserLogInDTO;
import com.Bookstore.book_store.web.service.UserAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthService userService;

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody @Valid UserLogInDTO userDTO) {
        String token = userService.logIn(userDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserDTO> logUp(@Valid @RequestBody RegisterUserDTO userDTO) {
        RegisterUserDTO user = userService.logUp(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
