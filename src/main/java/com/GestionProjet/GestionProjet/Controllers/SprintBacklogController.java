package com.GestionProjet.GestionProjet.Controllers;

import com.GestionProjet.GestionProjet.DTOClasses.SprintBacklogInputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.SprintBacklogOutputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.UserStoryInputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.UserStoryOutputDTO;
import com.GestionProjet.GestionProjet.Services.SprintBacklogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sprintBacklogs")
@RequiredArgsConstructor
public class SprintBacklogController {

    private final SprintBacklogService sprintBacklogService;

    // Récupérer tous les Sprint Backlogs
    @GetMapping
    public ResponseEntity<List<SprintBacklogOutputDTO>> getAllSprintBacklogs() {
        try {
            List<SprintBacklogOutputDTO> sprintBacklogs = sprintBacklogService.getAllSprintBacklogs();
            return ResponseEntity.ok(sprintBacklogs);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    // Créer un Sprint Backlog
    @PostMapping
    public ResponseEntity<SprintBacklogOutputDTO> createSprintBacklog(@RequestBody SprintBacklogInputDTO sprintBacklogInputDTO) {
        try {
            SprintBacklogOutputDTO createdSprintBacklog = sprintBacklogService.createSprintBacklog(sprintBacklogInputDTO);
            return ResponseEntity.ok(createdSprintBacklog);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Ajouter une UserStory existant au Sprint Backlog
    @PostMapping("/{sprintId}/userStories/{userStoryId}")
    public ResponseEntity<SprintBacklogOutputDTO> addUserStoryToSprint(@PathVariable Long sprintId, @PathVariable Long userStoryId) {
        try {
            SprintBacklogOutputDTO updatedSprintBacklog = sprintBacklogService.addUserStoryAuSprint(sprintId, userStoryId);
            return ResponseEntity.ok(updatedSprintBacklog);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Ajouter une nouvelle UserStory au Sprint Backlog
    @PostMapping("/{sprintId}/userStories")
    public ResponseEntity<SprintBacklogOutputDTO> addNewUserStoryToSprint(
            @PathVariable Long sprintId,
            @RequestBody UserStoryInputDTO userStoryInputDTO) {
        try {
            SprintBacklogOutputDTO updatedSprintBacklog = sprintBacklogService.addNewUserStoryToSprint(sprintId, userStoryInputDTO);
            return ResponseEntity.ok(updatedSprintBacklog);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Récupérer toutes les User Stories d'un Sprint Backlog
    @GetMapping("/{sprintId}/userStories")
    public ResponseEntity<List<UserStoryOutputDTO>> getUserStoriesBySprintBacklog(@PathVariable Long sprintId) {
        try {
            List<UserStoryOutputDTO> userStories = sprintBacklogService.getUserStoriesBySprintBacklog(sprintId);
            return ResponseEntity.ok(userStories);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Récupérer un Sprint Backlog par ID
    @GetMapping("/{id}")
    public ResponseEntity<SprintBacklogOutputDTO> getSprintBacklogById(@PathVariable Long id) {
        try {
            return sprintBacklogService.getSprintBacklogById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Mettre à jour un Sprint Backlog
    @PutMapping("/{id}")
    public ResponseEntity<SprintBacklogOutputDTO> updateSprintBacklog(@PathVariable Long id, @RequestBody SprintBacklogInputDTO sprintBacklogInputDTO) {
        try {
            SprintBacklogOutputDTO updatedSprintBacklog = sprintBacklogService.updateSprintBacklog(id, sprintBacklogInputDTO);
            return ResponseEntity.ok(updatedSprintBacklog);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer un Sprint Backlog
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprintBacklog(@PathVariable Long id) {
        try {
            sprintBacklogService.deleteSprintBacklog(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
