package com.GestionProjet.GestionProjet.Services;


import com.GestionProjet.GestionProjet.DTOClasses.SprintBacklogDTO;
import com.GestionProjet.GestionProjet.Entities.SprintBacklog;
import com.GestionProjet.GestionProjet.Entities.UserStory;

import java.util.List;

public interface SprintBacklogServiceInterface {

    // Créer un nouveau Sprint Backlog
    SprintBacklog creerSprintBacklog(SprintBacklog sprintBacklog);

    // Ajouter une User Story au Sprint Backlog
    SprintBacklog ajouterUserStoryAuSprint(Long sprintId, Long userStoryId);

    // Récupérer les User Stories d'un Sprint
    List<UserStory> getUserStoriesBySprint(Long sprintId);
}

