package com.Bookstore.book_store.utils;

import com.Bookstore.book_store.model.User;
import com.Bookstore.book_store.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthUtils {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Authentication isAuthenticated(String email,String password) {
        UserDetails userDetails = loadByEmail(email);
        log.info("userDetails: {}", userDetails.getUsername(), userDetails.getAuthorities());

        if (userDetails == null) {
            throw new UsernameNotFoundException("Invalid email");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword() )) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
    }
    private UserDetails loadByEmail(String email) {
        User userFromDB = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("User not found"));

        log.info("Email from: {}", userFromDB.getEmail(), userFromDB.getPassword());

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        userFromDB.getRoles().forEach(role ->
                grantedAuthorities
                        .add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName().name())))
        );

        return new org.springframework.security.core.userdetails.User(userFromDB.getUsername(),userFromDB.getPassword(),grantedAuthorities);
    }
}
