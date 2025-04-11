package com.GestionProjet.GestionProjet.Repositories;
import java.util.Optional;

import com.GestionProjet.GestionProjet.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
