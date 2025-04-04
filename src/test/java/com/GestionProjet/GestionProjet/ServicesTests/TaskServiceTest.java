package com.GestionProjet.GestionProjet.ServicesTests;

import com.GestionProjet.GestionProjet.DTOClasses.TaskInputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.TaskOutputDTO;
import com.GestionProjet.GestionProjet.Entities.Task;
import com.GestionProjet.GestionProjet.Repositories.TaskRepository;
import com.GestionProjet.GestionProjet.Services.Impl.TaskServiceImpl;
import com.GestionProjet.GestionProjet.enumeration.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.MockitoAnnotations;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private TaskInputDTO taskInputDTO;
    private TaskOutputDTO taskOutputDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Préparer l'objet Task (Entité)
        task = Task.builder()
                .id(1L)
                .title("Task 1")
                .description("Description 1")
                .status(Status.TO_DO)
                .build();

        // Préparer le TaskInputDTO (Entrée)
        taskInputDTO = TaskInputDTO.builder()
                .title("Task 1")
                .description("Description 1")
                .status(Status.TO_DO)
                .build();

        // Préparer le TaskOutputDTO (Sortie)
        taskOutputDTO = TaskOutputDTO.builder()
                .id(1L)
                .title("Task 1")
                .description("Description 1")
                .status(Status.TO_DO)
                .build();
    }

    @Test
    void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Appel au service
        TaskOutputDTO created = taskService.createTask(taskInputDTO);

        // Vérifier la création avec les valeurs du DTO
        assertEquals("Task 1", created.getTitle());
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task));

        List<TaskOutputDTO> tasks = taskService.getAllTasks();

        // Vérifier le nombre de tâches et l'id
        assertEquals(1, tasks.size());
        assertEquals(1L, tasks.get(0).getId());
    }

    @Test
    void testGetTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<TaskOutputDTO> found = taskService.getTaskById(1L);

        // Vérifier que la tâche a été trouvée
        assertTrue(found.isPresent());
        assertEquals("Task 1", found.get().getTitle());
    }

    @Test
    void testUpdateTask() {
        TaskInputDTO updatedDTO = TaskInputDTO.builder()
                .title("Updated Task")
                .description("Updated Description")
                .status(Status.DONE)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Appel de la mise à jour
        TaskOutputDTO result = taskService.updateTask(1L, updatedDTO);

        // Vérifier la mise à jour avec les valeurs du DTO
        assertEquals("Updated Task", result.getTitle());
        assertEquals(Status.DONE, result.getStatus());
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        // Vérifier que la suppression a bien eu lieu
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
