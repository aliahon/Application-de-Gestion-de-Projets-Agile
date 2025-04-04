package com.GestionProjet.GestionProjet.Services.Impl;

import com.GestionProjet.GestionProjet.DTOClasses.TaskInputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.TaskOutputDTO;
import com.GestionProjet.GestionProjet.Entities.Task;
import com.GestionProjet.GestionProjet.Repositories.TaskRepository;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import com.GestionProjet.GestionProjet.Services.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        task.setUserstory(userStoryRepository.findById(taskInputDTO.getUserStoryId())
                .orElseThrow(() -> new RuntimeException("UserStory not found")));

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
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskInputDTO.getTitle());
                    task.setDescription(taskInputDTO.getDescription());
                    task.setStatus(taskInputDTO.getStatus());
                    task.setUserstory(userStoryRepository.findById(taskInputDTO.getUserStoryId())
                            .orElseThrow(() -> new RuntimeException("UserStory not found")));

                    Task updatedTask = taskRepository.save(task);
                    return convertToOutputDTO(updatedTask);
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // Méthode pour supprimer une tâche
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Méthode pour convertir une Task en TaskOutputDTO
    private TaskOutputDTO convertToOutputDTO(Task task) {
        return TaskOutputDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .userStoryId(task.getUserstory().getId())  // Assurer de récupérer l'ID du UserStory
                .build();
    }
}
