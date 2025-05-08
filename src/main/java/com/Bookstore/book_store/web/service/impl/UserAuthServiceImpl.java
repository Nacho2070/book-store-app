package com.Bookstore.book_store.web.service.impl;

import com.Bookstore.book_store.exceptions.APIException;
import com.Bookstore.book_store.model.Role;
import com.Bookstore.book_store.model.User;
import com.Bookstore.book_store.utils.JwtUtils;
import com.Bookstore.book_store.utils.UserAuthUtils;
import com.Bookstore.book_store.web.payload.AppRole;
import com.Bookstore.book_store.web.payload.UserDTO;
import com.Bookstore.book_store.web.payload.UserLogInDTO;
import com.Bookstore.book_store.web.repository.UserRepository;
import com.Bookstore.book_store.web.repository.UserRolRepository;
import com.Bookstore.book_store.web.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;
    private final UserRolRepository rolRepository;

    private final ModelMapper modelMapper;
    private final UserAuthUtils userAuthUtils;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String logIn(UserLogInDTO userDTO) {

        Authentication authentication = this.userAuthUtils.isAuthenticated(userDTO.getMail(),userDTO.getPassword());
        log.info("current user: {}", authentication.getName(), authentication.getPrincipal());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.createToken(authentication);
    }

    @Override
    public UserDTO logUp(UserDTO userDTO) {

        Optional<User> userFromDB = userRepository.findByEmail(userDTO.getEmail());

        if (userFromDB.isPresent()) {
            throw new APIException("User already exists!");
        }

        User newUser = new User();
            newUser.setUsername(userDTO.getUsername());
            newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            newUser.setEmail(userDTO.getEmail());

            Set<String> strRoles = userDTO.getRole();
            Set<Role> roles = new HashSet<>();

            if (roles == null) {
                rolRepository.findByRoleName(AppRole.USER).orElseThrow(() ->
                        new APIException("Role does not exist"));
            } else {
                strRoles.forEach(role -> {
                    switch (role.toUpperCase()) {
                        case "ADMIN":
                            Role adminRol = rolRepository.findByRoleName(AppRole.ADMIN).orElseThrow(() ->
                                    new APIException("Role does not exist"));
                            roles.add(adminRol);
                            break;
                        case "USER":
                            Role userRol = rolRepository.findByRoleName(AppRole.USER).orElseThrow(() ->
                                    new APIException("Role does not exist"));
                            roles.add(userRol);
                            break;
                        default:
                            Role defaultRole = rolRepository.findByRoleName(AppRole.USER).orElseThrow(() ->
                                    new APIException("Role does not exist"));
                            roles.add(defaultRole);
                    }
                });
            }

        newUser.setRoles(roles);
        User userSaved = userRepository.save(newUser);
        return modelMapper.map(userSaved, UserDTO.class);
    }

}
