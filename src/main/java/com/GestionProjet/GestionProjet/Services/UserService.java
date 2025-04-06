package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.Entities.User;

import java.util.Optional;

public interface UserService {
    Integer saveUser(User user);

    Optional<User> findByUsername(String username);
}
