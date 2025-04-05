package com.GestionProjet.GestionProjet.Services.Impl;

import com.GestionProjet.GestionProjet.DTOClasses.TaskInputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.TaskOutputDTO;
import com.GestionProjet.GestionProjet.Entities.Task;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Repositories.TaskRepository;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import com.GestionProjet.GestionProjet.Services.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserStoryRepository userStoryRepository;

    // Méthode pour créer une tâche
    public TaskOutputDTO createTask(TaskInputDTO taskInputDTO) {
        // Convertir TaskInputDTO en entité Task
        Task task = new Task();
        task.setTitle(taskInputDTO.getTitle());
        task.setDescription(taskInputDTO.getDescription());
        task.setStatus(taskInputDTO.getStatus());

        // Convertir l'ID du UserStory (UserStoryDTO) en entité UserStory
        UserStory userStory = userStoryRepository.findById(taskInputDTO.getUserStoryId())
                .orElseThrow(() -> new RuntimeException("UserStory not found"));
        task.setUserstory(userStory);

        // Sauvegarder la tâche
        Task savedTask = taskRepository.save(task);

        // Retourner le TaskOutputDTO
        return convertToOutputDTO(savedTask);
    }

    // Méthode pour récupérer toutes les tâches
    public List<TaskOutputDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::convertToOutputDTO)
                .collect(Collectors.toList());
    }

    // Méthode pour récupérer une tâche par ID
    public Optional<TaskOutputDTO> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::convertToOutputDTO);
    }

    // Méthode pour mettre à jour une tâche
    public TaskOutputDTO updateTask(Long id, TaskInputDTO taskInputDTO) {
        // Find the task by its ID
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found")); // Ensure task is found, otherwise throw exception

        // Set task details
        task.setTitle(taskInputDTO.getTitle());
        task.setDescription(taskInputDTO.getDescription());
        task.setStatus(taskInputDTO.getStatus());
        task.setUserstory(userStoryRepository.findById(taskInputDTO.getUserStoryId())
                .orElseThrow(() -> new RuntimeException("UserStory not found"))); // Ensure UserStory exists

        // Save updated task
        Task updatedTask = taskRepository.save(task);

        // Convert to output DTO
        return convertToOutputDTO(updatedTask);
    }


    // Méthode pour supprimer une tâche
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Méthode pour convertir une Task en TaskOutputDTO
    public TaskOutputDTO convertToOutputDTO(Task task) {
        TaskOutputDTO taskOutputDTO = new TaskOutputDTO();
        taskOutputDTO.setId(task.getId());
        taskOutputDTO.setTitle(task.getTitle());
        taskOutputDTO.setDescription(task.getDescription());
        taskOutputDTO.setStatus(task.getStatus());

        // Assurer que userStoryId n'est pas null
        if (task.getUserstory() != null) {
            taskOutputDTO.setUserStoryId(task.getUserstory().getId());
        }

        return taskOutputDTO;
    }
}
