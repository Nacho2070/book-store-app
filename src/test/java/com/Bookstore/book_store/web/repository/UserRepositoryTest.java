package com.Bookstore.book_store.web.repository;

import com.Bookstore.book_store.model.User;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void userRepository_Save_ReturnsUser() {
        User user = new User();
        user.setUsername("Test User");
        user.setPassword("Test Password");
        user.setMail("test@test.com");
        User userSaved = userRepository.save(user);

        Assertions.assertThat(userSaved.getUsername()).isNotNull();
        Assertions.assertThat(userSaved.getUserId()).isGreaterThan(0);
    }
}