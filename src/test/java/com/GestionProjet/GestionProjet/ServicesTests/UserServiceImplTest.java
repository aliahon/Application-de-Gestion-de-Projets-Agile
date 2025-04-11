package com.GestionProjet.GestionProjet.ServicesTests;

import com.GestionProjet.GestionProjet.DTOClasses.UserCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.UserDTO;
import com.GestionProjet.GestionProjet.Entities.User;
import com.GestionProjet.GestionProjet.Repositories.UserRepository;
import com.GestionProjet.GestionProjet.Services.Impl.UserServiceImpl;
import com.GestionProjet.GestionProjet.enumeration.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = User.builder()
                .id(1L)
                .username("noha")
                .email("noha@example.com")
                .password("encodedPass")
                .role(Role.SCRUM_MASTER)
                .build();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        UserDTO result = userService.getUserById(1L);

        assertEquals("noha", result.getUsername());
        assertEquals("noha@example.com", result.getEmail());
        assertEquals(Role.SCRUM_MASTER, result.getRole());
    }

    @Test
    void testCreateUser() {
        UserCreateDTO dto = UserCreateDTO.builder()
                .username("noha")
                .email("noha@example.com")
                .password("123456")
                .role(Role.SCRUM_MASTER)
                .build();

        when(userRepository.existsByUsername("noha")).thenReturn(false);
        when(userRepository.existsByEmail("noha@example.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("encoded123456");
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        UserDTO result = userService.createUser(dto);

        assertEquals("noha", result.getUsername());
        assertEquals(Role.SCRUM_MASTER, result.getRole());
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.encode("newpass")).thenReturn("encodedNewPass");


        UserCreateDTO update = UserCreateDTO.builder()
                .username("newname")
                .email("new@email.com")
                .password("newpass")
                .role(Role.PRODUCT_OWNER)
                .build();

        UserDTO result = userService.updateUser(1L, update);

        assertEquals("newname", result.getUsername());
        assertEquals(Role.PRODUCT_OWNER, result.getRole());
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));

        List<UserDTO> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("noha", users.get(0).getUsername());
    }
}
