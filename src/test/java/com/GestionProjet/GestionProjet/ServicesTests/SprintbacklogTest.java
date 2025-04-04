package com.GestionProjet.GestionProjet.ServicesTests;

import com.GestionProjet.GestionProjet.DTOClasses.SprintBacklogOutputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.UserStoryOutputDTO;
import com.GestionProjet.GestionProjet.Entities.SprintBacklog;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Repositories.SprintBacklogRepository;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import com.GestionProjet.GestionProjet.Services.Impl.SprintBacklogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SprintBacklogServiceTest {

    @Mock
    private SprintBacklogRepository sprintBacklogRepository;

    @Mock
    private UserStoryRepository userStoryRepository;

    @InjectMocks
    private SprintBacklogServiceImpl sprintBacklogService;

    private SprintBacklog sprintBacklog;
    private UserStory userStory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Création d'un Sprint Backlog fictif
        sprintBacklog = new SprintBacklog();
        sprintBacklog.setId(1L);
        sprintBacklog.setNom("Sprint 1");
        sprintBacklog.setDateDebut(LocalDate.of(2024, 3, 1));
        sprintBacklog.setDateFin(LocalDate.of(2024, 3, 15));
        sprintBacklog.setUserStories(new ArrayList<>());

        // Création d'une User Story fictive
        userStory = new UserStory();
        userStory.setId(1L);
        userStory.setDescription("Créer la page d'authentification");
    }

    /*TEST 1 : Création d'un Sprint Backlog */
    @Test
    void testCreerSprintBacklog() {
        // Creating DTO for expected result
        SprintBacklogOutputDTO sprintBacklogOutputDTO = SprintBacklogOutputDTO.builder()
                .id(1L)
                .nom("Sprint 1")
                .dateDebut(LocalDate.of(2024, 3, 1))
                .dateFin(LocalDate.of(2024, 3, 15))
                .productBacklogId(1L) // Assuming it's set
                .build();

        when(sprintBacklogRepository.save(any(SprintBacklog.class))).thenReturn(sprintBacklog);

        SprintBacklogOutputDTO createdSprint = sprintBacklogService.creerSprintBacklog(sprintBacklog);

        assertNotNull(createdSprint);
        assertEquals("Sprint 1", createdSprint.getNom());
        verify(sprintBacklogRepository, times(1)).save(any(SprintBacklog.class));
    }

    /*TEST 2 : Ajouter une User Story à un Sprint */
    @Test
    void testAjouterUserStoryAuSprint() {
        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));
        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));
        when(sprintBacklogRepository.save(any(SprintBacklog.class))).thenReturn(sprintBacklog);

        SprintBacklogOutputDTO updatedSprint = sprintBacklogService.ajouterUserStoryAuSprint(1L, 1L);

        assertEquals(1, updatedSprint.getUserStories().size());
        assertEquals("Créer la page d'authentification", updatedSprint.getUserStories().get(0).getDescription());
        verify(sprintBacklogRepository, times(1)).findById(1L);
        verify(userStoryRepository, times(1)).findById(1L);
        verify(sprintBacklogRepository, times(1)).save(any(SprintBacklog.class));
    }

    /* TEST 4 : Récupérer les User Stories d'un Sprint */
    @Test
    void testGetUserStoriesBySprintBacklog() {
        UserStoryOutputDTO userStoryDTO = UserStoryOutputDTO.builder()
                .id(1L)
                .description("Créer la page d'authentification")
                .build();
        List<UserStoryOutputDTO> userStoryDTOs = List.of(userStoryDTO);

        sprintBacklog.setUserStories(List.of(userStory));

        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));

        List<UserStoryOutputDTO> userStories = sprintBacklogService.getUserStoriesBySprintBacklog(1L);

        assertNotNull(userStories);
        assertEquals(1, userStories.size());
        assertEquals("Créer la page d'authentification", userStories.get(0).getDescription());
        verify(sprintBacklogRepository, times(1)).findById(1L);
    }

    /*TEST 5 : Erreur si Sprint non trouvé */
    @Test
    void testAjouterUserStoryAuSprint_SprintNonTrouve() {
        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sprintBacklogService.ajouterUserStoryAuSprint(1L, 1L);
        });

        assertEquals("Sprint Backlog non trouvé", exception.getMessage());
        verify(sprintBacklogRepository, times(1)).findById(1L);
        verify(userStoryRepository, never()).findById(anyLong());
    }

    /*TEST 6 : Erreur si User Story non trouvée */
    @Test
    void testAjouterUserStoryAuSprint_UserStoryNonTrouvee() {
        when(sprintBacklogRepository.findById(1L)).thenReturn(Optional.of(sprintBacklog));
        when(userStoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            sprintBacklogService.ajouterUserStoryAuSprint(1L, 1L);
        });

        assertEquals("User Story non trouvée", exception.getMessage());
        verify(sprintBacklogRepository, times(1)).findById(1L);
        verify(userStoryRepository, times(1)).findById(1L);
    }
}
