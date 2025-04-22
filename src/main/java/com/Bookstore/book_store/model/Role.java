package com.Bookstore.book_store.model;

import com.Bookstore.book_store.web.payload.AppRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rolesId;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private AppRole roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role(AppRole userRole) {
        this.roleName = userRole;
    }

}
