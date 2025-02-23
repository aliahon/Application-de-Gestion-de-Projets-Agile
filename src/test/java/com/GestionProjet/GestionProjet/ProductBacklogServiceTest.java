package com.GestionProjet.GestionProjet;

import static org.junit.jupiter.api.Assertions.*;

import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import com.GestionProjet.GestionProjet.Entities.TechniquePriorisation;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Services.ProductBacklogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;



@SpringBootTest
public class ProductBacklogServiceTest {

    @Autowired
    private ProductBacklogService productBacklogService;

    @Test
    public void testCreateReadUpdateDelete() {

        // Create a ProductBacklog
        ProductBacklog backlog = new ProductBacklog();
        backlog.setNom("Test Backlog");
        backlog.setTechniquePriorisation(TechniquePriorisation.WSJF);
        backlog.setSprintDuration(7);

        // Create UserStory
        UserStory story = new UserStory();
        story.setProductBacklog(backlog);

        backlog.setUserStories(List.of(story));

        ProductBacklog savedBacklog = productBacklogService.createProductBacklog(backlog);
        assertNotNull(savedBacklog.getId());
        assertEquals(1, savedBacklog.getUserStories().size());

        // Read ProductBacklog
        List<ProductBacklog> backlogs = productBacklogService.getAllProductBacklogs();
        assertFalse(backlogs.isEmpty());
        System.out.println("All Backlogs: " + backlogs);

        // Update
        savedBacklog.setSprintDuration(10);
        ProductBacklog updatedBacklog = productBacklogService.updateProductBacklog(savedBacklog.getId(), savedBacklog);
        assertEquals(10, updatedBacklog.getSprintDuration());
        System.out.println("Updated : " + updatedBacklog);

        // Delete
        productBacklogService.deleteProductBacklog(savedBacklog.getId());
        assertNull(productBacklogService.getProductBacklogById(savedBacklog.getId()));
        System.out.println("Deleted ID: " + savedBacklog.getId());

    }
}

