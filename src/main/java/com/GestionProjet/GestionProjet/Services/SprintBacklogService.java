package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.SprintBacklogInputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.SprintBacklogOutputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.UserStoryOutputDTO;

import java.util.List;
import java.util.Optional;

public interface SprintBacklogService {

    // Créer un nouveau Sprint Backlog
    SprintBacklogOutputDTO creerSprintBacklog(SprintBacklogInputDTO sprintBacklogInputDTO);

    // Ajouter une User Story au Sprint Backlog
    SprintBacklogOutputDTO ajouterUserStoryAuSprint(Long sprintBacklogId, Long userStoryId);

    // Récupérer les User Stories d'un Sprint Backlog
    List<UserStoryOutputDTO> getUserStoriesBySprintBacklog(Long sprintBacklogId);

    List<SprintBacklogOutputDTO> getAllSprintBacklogs();

    // Récupérer un Sprint Backlog par ID
    Optional<SprintBacklogOutputDTO> getSprintBacklogById(Long id);

    // Mettre à jour un Sprint Backlog
    SprintBacklogOutputDTO updateSprintBacklog(Long id, SprintBacklogInputDTO sprintBacklogInputDTO);

    // Supprimer un Sprint Backlog
    void deleteSprintBacklog(Long id);
}
