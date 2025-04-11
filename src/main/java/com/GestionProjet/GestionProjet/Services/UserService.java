package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.UserCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(UserCreateDTO user);
    UserDTO updateUser(Long id, UserCreateDTO updatedUser);
    void deleteUser(Long id);
}
