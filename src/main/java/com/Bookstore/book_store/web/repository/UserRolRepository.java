package com.Bookstore.book_store.web.repository;

import com.Bookstore.book_store.model.Role;
import com.Bookstore.book_store.web.payload.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRolRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByRoleName(AppRole role);
}
