package com.GestionProjet.GestionProjet.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.GestionProjet.GestionProjet.DTOClasses.ProjetDTO;
import com.GestionProjet.GestionProjet.Entities.Projet;
import com.GestionProjet.GestionProjet.Repositories.ProjetRepository;
import com.GestionProjet.GestionProjet.Services.ProjetService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

public class ProjetServiceTest {

    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private ProjetService projetService;

    private Projet projet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projet = Projet.builder()
                .nom("Test Projet")
                .description("Projet de test")
                .build();

        when(projetRepository.save(any(Projet.class))).thenAnswer(invocation -> {
            Projet savedProjet = invocation.getArgument(0);
            savedProjet.setId(1L);
            return savedProjet;
        });
    }

    @Test
    void testCreateProjet() {
        Projet savedProjet = projetService.createProjet(projet);
        assertNotNull(savedProjet);
        assertEquals("Test Projet", savedProjet.getNom());
        assertEquals(1L, savedProjet.getId());
        System.out.println("Created Projet: " + savedProjet);
    }

    @Test
    void testUpdateProjet() {
        // Arrange
        ProjetDTO projetDTO = ProjetDTO.builder()
                .nom("Updated Projet")
                .description("Updated description")
                .build();

        // Mock repository behavior
        when(projetRepository.findById(anyLong())).thenReturn(Optional.of(projet));
        when(projetRepository.save(any(Projet.class))).thenReturn(projet);

        // Act
        Projet updatedProjet = projetService.updateProjet(1L, projetDTO);

        // Assert
        assertNotNull(updatedProjet);
        assertEquals("Updated Projet", updatedProjet.getNom());
        assertEquals("Updated description", updatedProjet.getDescription());
        System.out.println("Updated Projet: " + updatedProjet);
    }

    @Test
    void testDeleteProjet() {
        // Act
        projetService.deleteProjet(1L);

        // Assert
        verify(projetRepository, times(1)).deleteById(1L); // Verify that deleteById was called once
        System.out.println("Delete verified.");
    }
}
