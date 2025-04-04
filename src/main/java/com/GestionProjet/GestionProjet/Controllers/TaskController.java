package com.GestionProjet.GestionProjet.Controllers;

import com.GestionProjet.GestionProjet.DTOClasses.TaskInputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.TaskOutputDTO;
import com.GestionProjet.GestionProjet.Services.Impl.TaskServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImpl taskService;

    // Création d'une tâche
    @PostMapping
    public ResponseEntity<TaskOutputDTO> createTask(@RequestBody TaskInputDTO taskInputDTO) {
        TaskOutputDTO created = taskService.createTask(taskInputDTO);
        return ResponseEntity.ok(created);
    }

    // Récupérer toutes les tâches
    @GetMapping
    public ResponseEntity<List<TaskOutputDTO>> getAllTasks() {
        List<TaskOutputDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Récupérer une tâche par son ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskOutputDTO> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mettre à jour une tâche
    @PutMapping("/{id}")
    public ResponseEntity<TaskOutputDTO> updateTask(@PathVariable Long id, @RequestBody TaskInputDTO taskInputDTO) {
        try {
            TaskOutputDTO updated = taskService.updateTask(id, taskInputDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer une tâche
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
