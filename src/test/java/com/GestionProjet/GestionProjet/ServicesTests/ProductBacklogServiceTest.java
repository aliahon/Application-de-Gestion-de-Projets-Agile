package com.GestionProjet.GestionProjet.ServicesTests;

import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogDTO;
import com.GestionProjet.GestionProjet.DTOClasses.ProjetDTO;
import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import com.GestionProjet.GestionProjet.Entities.Projet;
import com.GestionProjet.GestionProjet.Repositories.ProductBacklogRepository;
import com.GestionProjet.GestionProjet.Repositories.ProjetRepository;
import com.GestionProjet.GestionProjet.Services.Impl.ProjetServiceImpl;
import com.GestionProjet.GestionProjet.Services.Impl.ProductBacklogServiceImpl;
import com.GestionProjet.GestionProjet.enumeration.TechniquePriorisation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
class ProductBacklogServiceImplTest {

    @Mock
    private ProductBacklogRepository productBacklogRepository;

    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private ProductBacklogServiceImpl productBacklogService;

    private ProductBacklog backlog;
    private ProductBacklogDTO backlogDTO;
    private ProductBacklogCreateDTO backlogCreateDTO;
    private Projet projet;

    @BeforeEach
    void setUp() {
        projet = Projet.builder()
                .projet_id(1L)
                .nom("Test Project")
                .build();

        backlog = ProductBacklog.builder()
                .id(1L)
                .nom("Test Backlog")
                .techniquePriorisation(TechniquePriorisation.MoSCoW)
                .projet(projet)
                .build();

        backlogDTO = ProductBacklogDTO.builder()
                .id(backlog.getId())
                .nom("Test Backlog")
                .techniquePriorisation(TechniquePriorisation.MoSCoW)
                .projet_id(1L)
                .build();

        backlogCreateDTO = ProductBacklogCreateDTO.builder()
                .nom("Test Backlog")
                .techniquePriorisation("MoSCoW")
                .projet_id(1L)
                .build();
    }

    @Test
    void testGetAllProductBacklogs() {
        // Arrange
        when(productBacklogRepository.findAll()).thenReturn(Arrays.asList(backlog));

        // Act
        var result = productBacklogService.getAllProductBacklogs();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(backlogDTO.getNom(), result.get(0).getNom());
    }

    @Test
    void testGetProductBacklogById() {
        // Arrange
        when(productBacklogRepository.findById(1L)).thenReturn(Optional.of(backlog));

        // Act
        var result = productBacklogService.getProductBacklogById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(backlogDTO.getNom(), result.getNom());
    }

    @Test
    void testCreateProductBacklog() {
        // Arrange
        when(projetRepository.findById(1L)).thenReturn(Optional.of(projet));
        when(productBacklogRepository.save(any(ProductBacklog.class))).thenReturn(backlog);

        // Act
        ProductBacklogDTO createdBacklogDTO = productBacklogService.createProductBacklog(backlogCreateDTO);

        // Assert
        assertNotNull(createdBacklogDTO);
        assertEquals(backlogDTO.getNom(), createdBacklogDTO.getNom());
        verify(productBacklogRepository, times(1)).save(any(ProductBacklog.class));
    }

    @Test
    void testUpdateProductBacklog() {
        // Arrange
        when(productBacklogRepository.findById(1L)).thenReturn(Optional.of(backlog));
        when(productBacklogRepository.save(any(ProductBacklog.class))).thenReturn(backlog);

        // Act
        ProductBacklogDTO updatedBacklogDTO = productBacklogService.updateProductBacklog(1L, backlogDTO);

        // Assert
        assertNotNull(updatedBacklogDTO);
        assertEquals(backlogDTO.getNom(), updatedBacklogDTO.getNom());
        verify(productBacklogRepository, times(1)).save(any(ProductBacklog.class));
    }

    @Test
    void testDeleteProductBacklog() {
        // Arrange
        doNothing().when(productBacklogRepository).deleteById(1L);

        // Act
        productBacklogService.deleteProductBacklog(1L);

        // Assert
        verify(productBacklogRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetProductBacklogByIdNotFound() {
        // Arrange
        when(productBacklogRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        var result = productBacklogService.getProductBacklogById(1L);

        // Assert
        assertNull(result);
    }
}
