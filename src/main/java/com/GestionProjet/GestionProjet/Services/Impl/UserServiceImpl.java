package com.GestionProjet.GestionProjet.Services.Impl;

import com.GestionProjet.GestionProjet.DTOClasses.UserCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.UserDTO;
import com.GestionProjet.GestionProjet.Entities.User;
import com.GestionProjet.GestionProjet.Repositories.UserRepository;
import com.GestionProjet.GestionProjet.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::userToDto)
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userToDto(user);
    }

    @Override
    public UserDTO createUser(UserCreateDTO dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User saved = userRepository.save(User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(encodedPassword)
                .role(dto.getRole())
                .build());

        return userToDto(saved);
    }

    @Override
    public UserDTO updateUser(Long id, UserCreateDTO updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) {
            existingUser.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }

        User saved = userRepository.save(existingUser);
        return userToDto(saved);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Mapper
    private UserDTO userToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .username(user.getUsername())
                .build();
    }
}


