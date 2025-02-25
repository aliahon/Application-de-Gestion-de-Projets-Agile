package com.GestionProjet.GestionProjet.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import com.GestionProjet.GestionProjet.Entities.TechniquePriorisation;
import com.GestionProjet.GestionProjet.Repositories.ProductBacklogRepository;
import com.GestionProjet.GestionProjet.Services.ProductBacklogService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductBacklogServiceTest {

    @Autowired
    private ProductBacklogRepository productBacklogRepository;

    @Autowired
    private ProductBacklogService productBacklogService;

    private ProductBacklog backlog;

    @BeforeEach
    void setUp() {
        backlog = new ProductBacklog();
        backlog.setNom("Test Backlog");
        backlog.setTechniquePriorisation(TechniquePriorisation.WSJF);
        backlog.setSprintDuration(7);
        productBacklogRepository.save(backlog);
    }

    @Test
    void testCreateProductBacklog() {
        ProductBacklog savedBacklog = productBacklogService.createProductBacklog(backlog);
        assertNotNull(savedBacklog);
        assertEquals("Test Backlog", savedBacklog.getNom());
        System.out.println("Created Backlog: " + savedBacklog);
    }

    @Test
    void testUpdateProductBacklog() {
        backlog.setSprintDuration(14);

        ProductBacklog updatedBacklog = productBacklogService.updateProductBacklog(1L, backlog);
        assertNotNull(updatedBacklog);
        assertEquals(14, updatedBacklog.getSprintDuration());
        System.out.println("Updated Backlog: " + updatedBacklog);
    }

    @Test
    void testDeleteProductBacklog() {
        productBacklogService.deleteProductBacklog(1L);
        Optional<ProductBacklog> res = productBacklogRepository.findById(backlog.getId());
        assertTrue(res.isPresent());
    }
}

