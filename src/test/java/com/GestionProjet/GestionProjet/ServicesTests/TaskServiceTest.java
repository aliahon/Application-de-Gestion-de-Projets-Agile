package com.GestionProjet.GestionProjet.ServicesTests;

import com.GestionProjet.GestionProjet.DTOClasses.TaskInputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.TaskOutputDTO;
import com.GestionProjet.GestionProjet.Entities.Task;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Repositories.TaskRepository;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import com.GestionProjet.GestionProjet.Services.Impl.TaskServiceImpl;
import com.GestionProjet.GestionProjet.enumeration.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserStoryRepository userStoryRepository;

    private UserStory userStory;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userStory = new UserStory();
        userStory.setId(1L);
        userStory.setTitle("Test UserStory");
        // Optionally, you can set up a Task object here for other tests
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Description of the task");
        task.setStatus(Status.IN_PROGRESS);
        task.setUserstory(userStory);

        // Mocking repository calls as needed, for example:
        when(taskRepository.save(any(Task.class))).thenReturn(task);

    }

    @Test
    void testCreateTask() {
        // Mock the UserStory repository
        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));

        TaskInputDTO taskInputDTO = TaskInputDTO.builder()
                .title("Test Task")
                .description("Description of the task")
                .status(Status.IN_PROGRESS)
                .userStoryId(1L) // Set the UserStory ID here
                .build();

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Description of the task");
        task.setStatus(Status.IN_PROGRESS);
        task.setUserstory(userStory);

        // Mock the save method to return the task
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Call the service
        TaskOutputDTO result = taskService.createTask(taskInputDTO);

        // Assert the task creation
        assertEquals("Test Task", result.getTitle());
        assertEquals("Description of the task", result.getDescription());
        assertEquals(Status.IN_PROGRESS, result.getStatus());
        assertEquals(1L, result.getUserStoryId()); // Verify that userStoryId was correctly set
    }

    @Test
    void testGetAllTasks() {
        // Prepare mock tasks
        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Test Task 1");
        task1.setDescription("Description of task 1");
        task1.setStatus(Status.DONE);
        task1.setUserstory(userStory);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setTitle("Test Task 2");
        task2.setDescription("Description of task 2");
        task2.setStatus(Status.IN_PROGRESS);
        task2.setUserstory(userStory);

        // Mock findAll to return the tasks
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        // Call the service
        List<TaskOutputDTO> result = taskService.getAllTasks();

        // Assert the results
        assertEquals(2, result.size());
        assertEquals("Test Task 1", result.get(0).getTitle());
        assertEquals("Test Task 2", result.get(1).getTitle());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Description of the task");
        task.setStatus(Status.IN_PROGRESS);
        task.setUserstory(userStory);

        // Mock findById to return the task
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Call the service
        Optional<TaskOutputDTO> result = taskService.getTaskById(1L);

        // Assert the result
        assertTrue(result.isPresent());
        assertEquals("Test Task", result.get().getTitle());
    }

    @Test
    void testUpdateTask() {
        // Prepare mock task
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Old Task");
        task.setDescription("Old description");
        task.setStatus(Status.TO_DO);
        task.setUserstory(userStory);

        // Mock the repository to return the task when findById is called
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Mock the UserStory repository
        when(userStoryRepository.findById(1L)).thenReturn(Optional.of(userStory));

        TaskInputDTO taskInputDTO = TaskInputDTO.builder()
                .title("Updated Task")
                .description("Updated description")
                .status(Status.IN_PROGRESS)
                .userStoryId(1L)
                .build();

        // Call the service
        TaskOutputDTO updatedTask = taskService.updateTask(1L, taskInputDTO);

        // Assert the update
        assertEquals("Updated Task", updatedTask.getTitle());
        assertEquals("Updated description", updatedTask.getDescription());
        assertEquals(Status.IN_PROGRESS, updatedTask.getStatus());
        assertEquals(1L, updatedTask.getUserStoryId()); // Ensure UserStoryId is correctly set
    }

    @Test
    void testDeleteTask() {
        // Prepare a task to delete
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task to delete");
        task.setDescription("Description of the task to delete");
        task.setStatus(Status.DONE);
        task.setUserstory(userStory);

        // Mock the deleteById method
        doNothing().when(taskRepository).deleteById(1L);

        // Call the service
        taskService.deleteTask(1L);

        // Verify that deleteById was called with the correct argument
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
