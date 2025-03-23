package com.GestionProjet.GestionProjet.ServicesTests;

import com.GestionProjet.GestionProjet.DTOClasses.ProjetCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.ProjetDTO;
import com.GestionProjet.GestionProjet.Entities.Projet;
import com.GestionProjet.GestionProjet.Repositories.ProjetRepository;
import com.GestionProjet.GestionProjet.Services.Impl.ProjetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjetServiceImplTest {

    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private ProjetServiceImpl projetService;

    private Projet projet;
    private ProjetDTO projetDTO;
    private ProjetCreateDTO projetCreateDTO;

    @BeforeEach
    void setUp() {
        projet = Projet.builder()
                .projet_id(1L)
                .nom("Projet Test")
                .description("Description Test")
                .build();

        projetDTO = ProjetDTO.builder()
                .id(1L)
                .nom("Projet Test")
                .description("Description Test")
                .build();
        projetCreateDTO = ProjetCreateDTO.builder()
                .nom("Projet Test")
                .description("Description Test")
                .build();
    }

    @Test
    void testGetAllProjets() {
        when(projetRepository.findAll()).thenReturn(Arrays.asList(projet));

        List<ProjetDTO> result = projetService.getAllProjets();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Projet Test", result.get(0).nom);
    }

    @Test
    void testGetProjetById() {
        when(projetRepository.findById(1L)).thenReturn(Optional.of(projet));

        ProjetDTO result = projetService.getProjetById(1L);

        assertNotNull(result);
        assertEquals("Projet Test", result.nom);
    }

    @Test
    void testCreateProjet() {
        when(projetRepository.save(any(Projet.class))).thenReturn(projet);

        ProjetDTO result = projetService.createProjet(projetCreateDTO);

        assertNotNull(result);
        assertEquals("Projet Test", result.nom);
    }

    @Test
    void testUpdateProjet() {
        when(projetRepository.findById(1L)).thenReturn(Optional.of(projet));
        when(projetRepository.save(any(Projet.class))).thenReturn(projet);

        ProjetDTO updatedDTO = ProjetDTO.builder()
                .nom("Updated Projet")
                .description("Updated Description")
                .build();

        ProjetDTO result = projetService.updateProjet(1L, updatedDTO);

        assertNotNull(result);
        assertEquals("Updated Projet", result.nom);
    }

    @Test
    void testDeleteProjet() {
        doNothing().when(projetRepository).deleteById(1L);

        projetService.deleteProjet(1L);

        verify(projetRepository, times(1)).deleteById(1L);
    }
}
