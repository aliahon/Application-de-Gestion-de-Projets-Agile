package com.GestionProjet.GestionProjet.ServicesTests;
import com.GestionProjet.GestionProjet.Entities.Priority;
import com.GestionProjet.GestionProjet.Entities.Status;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import com.GestionProjet.GestionProjet.Services.UserStoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class UserStoryServiceTest {

    @Autowired
    private UserStoryRepository userStoryRepository;

    @Autowired
    private UserStoryService userStoryService;

    private UserStory userStory;

    @BeforeEach
    void setUp() {
        userStory = UserStory.builder()
                .title("Créer un Product Backlog")
                .description("Permettre au Product Owner de créer un backlog")
                .priority(Priority.HIGH)
                .status(Status.TO_DO)
                .build();
        userStory = userStoryRepository.save(userStory);
    }

    @Test
    void testGetAllUserStories() {
        List<UserStory> result = userStoryService.getAllUserStories();
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Créer un Product Backlog", result.get(0).getTitle());
    }

    @Test
    void testGetUserStoryById_Found() {
        Optional<UserStory> result = Optional.ofNullable(userStoryService.getUserStoryById(userStory.getId()));
        assertTrue(result.isPresent());
        assertEquals("Créer un Product Backlog", result.get().getTitle());
    }

    @Test
    void testGetUserStoryById_NotFound() {
        Optional<UserStory> result = Optional.ofNullable(userStoryService.getUserStoryById(999L));
        assertFalse(result.isPresent());
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
        assertNotNull(result.getId());
        assertEquals("Nouvelle User Story", result.getTitle());
    }

    @Test
    void testUpdateUserStory_Found() {
        UserStory updatedUserStory = UserStory.builder()
                .title("Mise à jour du Backlog")
                .description("Modification du backlog")
                .priority(Priority.MEDIUM)
                .status(Status.IN_PROGRESS)
                .build();

        UserStory result = userStoryService.updateUserStory(userStory.getId(), updatedUserStory);
        assertNotNull(result);
        assertEquals("Mise à jour du Backlog", result.getTitle());
        assertEquals(Status.IN_PROGRESS, result.getStatus());
    }

    @Test
    void testUpdateUserStory_NotFound() {
        UserStory updatedUserStory = UserStory.builder()
                .title("Mise à jour inexistante")
                .description("Ne devrait pas fonctionner")
                .priority(Priority.LOW)
                .status(Status.DONE)
                .build();

        UserStory result = userStoryService.updateUserStory(999L, updatedUserStory);

        assertNull(result);
    }

    @Test
    void testDeleteUserStory() {
        userStoryService.deleteUserStory(userStory.getId());
        Optional<UserStory> result = userStoryRepository.findById(userStory.getId());
        assertFalse(result.isPresent());
    }
}
