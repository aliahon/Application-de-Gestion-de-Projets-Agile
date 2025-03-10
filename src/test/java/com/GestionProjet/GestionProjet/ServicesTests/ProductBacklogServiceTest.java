package com.GestionProjet.GestionProjet.ServicesTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogDTO;
import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import com.GestionProjet.GestionProjet.Entities.TechniquePriorisation;
import com.GestionProjet.GestionProjet.Repositories.ProductBacklogRepository;
import com.GestionProjet.GestionProjet.Services.ProductBacklogService;
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
        backlog.setTechniquePriorisation(TechniquePriorisation.WSJF);
        backlog.setSprintDuration(7);

        when(productBacklogRepository.save(any(ProductBacklog.class))).thenAnswer(invocation -> {
            ProductBacklog savedBacklog = invocation.getArgument(0);
            savedBacklog.setId(1L);
            return savedBacklog;
        });
    }

    @Test
    void testCreateProductBacklog() {
        ProductBacklog savedBacklog = productBacklogService.createProductBacklog(backlog);
        assertNotNull(savedBacklog);
        assertEquals("Test Backlog", savedBacklog.getNom());
        assertEquals(1L, savedBacklog.getId());
        System.out.println("Created Backlog: " + savedBacklog);
    }

    @Test
    void testUpdateProductBacklog() {
        ProductBacklogDTO dto = new ProductBacklogDTO();
        dto.nom = "Updated Backlog";
        dto.sprintDuration = 14;

        when(productBacklogRepository.findById(anyLong())).thenReturn(Optional.of(backlog));
        when(productBacklogRepository.save(any(ProductBacklog.class))).thenReturn(backlog);

        ProductBacklog updatedBacklog = productBacklogService.updateProductBacklog(1L, dto);
        assertNotNull(updatedBacklog);
        assertEquals("Updated Backlog", updatedBacklog.getNom());
        assertEquals(14, updatedBacklog.getSprintDuration());
        System.out.println("Updated Backlog: " + updatedBacklog);
    }

    @Test
    void testDeleteProductBacklog() {
        productBacklogService.deleteProductBacklog(1L);
        verify(productBacklogRepository, times(1)).deleteById(1L);
        System.out.println("Delete verified.");
    }
}
