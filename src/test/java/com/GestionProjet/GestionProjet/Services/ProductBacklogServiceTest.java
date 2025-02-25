package com.GestionProjet.GestionProjet.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import com.GestionProjet.GestionProjet.Repositories.ProductBacklogRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

public class ProductBacklogServiceTest {

    @Mock
    private ProductBacklogRepository productBacklogRepository;

    @InjectMocks
    private ProductBacklogService productBacklogService;

    private ProductBacklog backlog;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        backlog = new ProductBacklog();
        backlog.setNom("Test Backlog");
        backlog.setTechniquePriorisation("WSJF");
        backlog.setSprintDuration(7);

        when(productBacklogRepository.save(any(ProductBacklog.class))).thenReturn(backlog);
        when(productBacklogRepository.findById(anyLong())).thenReturn(Optional.of(backlog));
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

        when(productBacklogRepository.existsById(1L)).thenReturn(true);
        when(productBacklogRepository.save(backlog)).thenReturn(backlog);

        ProductBacklog updatedBacklog = productBacklogService.updateProductBacklog(1L, backlog);
        assertNotNull(updatedBacklog);
        assertEquals(14, updatedBacklog.getSprintDuration());
        System.out.println("Updated Backlog: " + updatedBacklog);
    }

    @Test
    void testDeleteProductBacklog() {
        productBacklogService.deleteProductBacklog(1L);
        verify(productBacklogRepository, times(1)).deleteById(1L);
        System.out.println("Delete successful.");
    }
}

