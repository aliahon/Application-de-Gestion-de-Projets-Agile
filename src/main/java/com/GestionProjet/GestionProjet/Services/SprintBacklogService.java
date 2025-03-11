package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.Entities.SprintBacklog;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Repositories.SprintBacklogRepository;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintBacklogService implements SprintBacklogServiceInterface {

    private final SprintBacklogRepository sprintBacklogRepository;
    private final UserStoryRepository userStoryRepository;

    @Autowired
    public SprintBacklogService(SprintBacklogRepository sprintBacklogRepository, UserStoryRepository userStoryRepository) {
        this.sprintBacklogRepository = sprintBacklogRepository;
        this.userStoryRepository = userStoryRepository;
    }

    // Créer un nouveau Sprint Backlog
    public SprintBacklog creerSprintBacklog(SprintBacklog sprintBacklog){
        if(sprintBacklog.getDateDebut().isAfter(sprintBacklog.getDateFin())){
            throw new IllegalArgumentException("La date de debut doit etre posterieur a la date de fin ");
        }
        if(sprintBacklog.getNom()==null){
            throw new IllegalArgumentException("Le nom ne peut pas etre null ");
        }
        return sprintBacklogRepository.save(sprintBacklog);
    }

    // Ajouter une UserStory au Sprint Backlog
    public SprintBacklog ajouterUserStoryAuSprint(Long sprintId, Long userStoryId) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint Backlog non trouvé"));

        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new RuntimeException("User Story non trouvée"));

        sprintBacklog.ajouterUserStory(userStory);
        return sprintBacklogRepository.save(sprintBacklog);
    }

    // Obtenir toutes les User Stories d'un Sprint Backlog
    public List<UserStory> getUserStoriesBySprint(Long sprintId) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Sprint Backlog non trouvé"));

        return sprintBacklog.getUserStories();
    }
}
