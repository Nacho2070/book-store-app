package com.Bookstore.book_store.utils;

import com.Bookstore.book_store.exceptions.APIException;
import com.Bookstore.book_store.model.User;
import com.Bookstore.book_store.web.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthUtil {

    @Autowired
    UserRepository userRepository;

    public String loggedInEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication {}", authentication.getName());

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new APIException("User Not Found with username " + authentication.getName()));
        return user.getEmail();
    }

    public User loggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new APIException("User Not Found with username: " + authentication.getName()));
        return user;

    }
}
