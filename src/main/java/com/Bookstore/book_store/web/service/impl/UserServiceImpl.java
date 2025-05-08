package com.Bookstore.book_store.web.service.impl;

import com.Bookstore.book_store.model.User;
import com.Bookstore.book_store.web.payload.UserDTO;
import com.Bookstore.book_store.web.repository.UserRepository;
import com.Bookstore.book_store.web.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDTO> getUser() {

        List<User>users = userRepository.findAll();

        return users.stream().map(user -> {
                    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                    Set<String> roles = new HashSet<>();
                    user.getRoles().forEach(role -> roles.add(role.getRoleName().toString()));
                    userDTO.setRole(roles);
                    return userDTO;
                }
            ).toList();
    }

}
