package com.GestionProjet.GestionProjet.ServicesTests;

import com.GestionProjet.GestionProjet.Entities.Epic;
import com.GestionProjet.GestionProjet.Repositories.EpicRepository;
import com.GestionProjet.GestionProjet.Services.Impl.EpicServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EpicServiceTest {

    @Mock
    private EpicRepository epicRepository;

    @InjectMocks
    private EpicServiceImpl epicService;

    private Epic epic;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        epic = new Epic();
        epic.setId(1L);
    }

    @Test
    public void testGetAllEpics() {
        // Arrange
        when(epicRepository.findAll()).thenReturn(List.of(epic));

        // Act
        List<Epic> epics = epicService.getAllEpics();

        // Assert
        assertNotNull(epics);
        assertEquals(1, epics.size());
        assertEquals(epic.getId(), epics.get(0).getId());
        verify(epicRepository, times(1)).findAll();
    }

    @Test
    public void testGetEpicById() {
        // Arrange
        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));

        // Act
        Optional<Epic> result = epicService.getEpicById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(epic.getId(), result.get().getId());
        verify(epicRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateEpic() {
        // Arrange
        when(epicRepository.save(epic)).thenReturn(epic);

        // Act
        Epic createdEpic = epicService.createEpic(epic);

        // Assert
        assertNotNull(createdEpic);
        assertEquals(epic.getId(), createdEpic.getId());
        verify(epicRepository, times(1)).save(epic);
    }

    @Test
    public void testUpdateEpic() {
        // Arrange
        Epic updatedEpic = new Epic();
        updatedEpic.setId(1L);
        updatedEpic.setProductBacklog(epic.getProductBacklog());

        when(epicRepository.findById(1L)).thenReturn(Optional.of(epic));
        when(epicRepository.save(epic)).thenReturn(updatedEpic);

        // Act
        Epic result = epicService.updateEpic(1L, updatedEpic);

        // Assert
        assertNotNull(result);
        assertEquals(updatedEpic.getId(), result.getId());
        verify(epicRepository, times(1)).findById(1L);
        verify(epicRepository, times(1)).save(updatedEpic);
    }

    @Test
    public void testDeleteEpic() {
        // Arrange
        doNothing().when(epicRepository).deleteById(1L);

        // Act
        epicService.deleteEpic(1L);

        // Assert
        verify(epicRepository, times(1)).deleteById(1L);
    }
}
