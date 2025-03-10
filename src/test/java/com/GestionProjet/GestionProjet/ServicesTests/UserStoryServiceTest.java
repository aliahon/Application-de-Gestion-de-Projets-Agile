package com.GestionProjet.GestionProjet.ServicesTests;

import com.GestionProjet.GestionProjet.DTOClasses.UserStoryDTO;
import com.GestionProjet.GestionProjet.Entities.Priority;
import com.GestionProjet.GestionProjet.Entities.Status;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import com.GestionProjet.GestionProjet.Services.UserStoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserStoryServiceTest {

    @Mock
    private UserStoryRepository userStoryRepository;

    @InjectMocks
    private UserStoryService userStoryService;

    private UserStory userStory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userStory = UserStory.builder()
                .title("Créer un Product Backlog")
                .description("Permettre au Product Owner de créer un backlog")
                .priority(Priority.HIGH)
                .status(Status.TO_DO)
                .build();

        when(userStoryRepository.save(any(UserStory.class))).thenAnswer(invocation -> {
            UserStory savedUserStory = invocation.getArgument(0);
            savedUserStory.setId(1L);
            return savedUserStory;
        });
    }

    @Test
    void testGetAllUserStories() {
        when(userStoryRepository.findAll()).thenReturn(List.of(userStory));
        List<UserStory> result = userStoryService.getAllUserStories();

        System.out.println("Results: " + result);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Créer un Product Backlog", result.get(0).getTitle());
    }




    @Test
    void testCreateUserStory() {
        UserStory newUserStory = UserStory.builder()
                .title("Nouvelle User Story")
                .description("Description test")
                .priority(Priority.MEDIUM)
                .status(Status.IN_PROGRESS)
                .build();

        UserStory result = userStoryService.createUserStory(newUserStory);

        assertNotNull(result);
        assertEquals("Nouvelle User Story", result.getTitle());
        assertEquals(1L, result.getId());
        System.out.println("Created User Story: " + result);
    }

    @Test
    void testUpdateUserStory_Found() {
        UserStoryDTO dto = new UserStoryDTO();
        dto.title = "Mise à jour du Backlog";
        dto.description = "Modification du backlog";

        when(userStoryRepository.findById(userStory.getId())).thenReturn(Optional.of(userStory));
        when(userStoryRepository.save(any(UserStory.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserStory result = userStoryService.updateUserStory(userStory.getId(), dto);

        System.out.println("Updated User Story: " + result);

        assertNotNull(result);
        assertEquals("Mise à jour du Backlog", result.getTitle());
        assertEquals("Modification du backlog", result.getDescription());
    }


    @Test
    void testUpdateUserStory_NotFound() {
        UserStoryDTO dto = new UserStoryDTO();
        dto.title = "Mise à jour inexistante";
        dto.description = "Ne devrait pas fonctionner";

        when(userStoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        UserStory result = userStoryService.updateUserStory(999L, dto);

        System.out.println("Update Not Found: " + result);

        assertNull(result);
    }

    @Test
    void testDeleteUserStory() {
        doNothing().when(userStoryRepository).deleteById(anyLong());
        userStoryService.deleteUserStory(userStory.getId());

        System.out.println("Deleted User Story with ID: " + userStory.getId());

        verify(userStoryRepository, times(1)).deleteById(userStory.getId());
    }
}