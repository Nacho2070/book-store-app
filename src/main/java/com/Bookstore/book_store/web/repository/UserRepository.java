package com.Bookstore.book_store.web.repository;

import com.Bookstore.book_store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //@Query("select u.email FROM User u where u.email = :email")
    Optional<User> findByEmail (String email);
}
