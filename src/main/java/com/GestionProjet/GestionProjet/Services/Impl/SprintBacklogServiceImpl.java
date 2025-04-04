package com.GestionProjet.GestionProjet.Services.Impl;

import com.GestionProjet.GestionProjet.DTOClasses.SprintBacklogInputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.SprintBacklogOutputDTO;
import com.GestionProjet.GestionProjet.DTOClasses.UserStoryOutputDTO;
import com.GestionProjet.GestionProjet.Entities.SprintBacklog;
import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Repositories.SprintBacklogRepository;
import com.GestionProjet.GestionProjet.Repositories.ProductBacklogRepository;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import com.GestionProjet.GestionProjet.Services.SprintBacklogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SprintBacklogServiceImpl implements SprintBacklogService {

    private final SprintBacklogRepository sprintBacklogRepository;
    private final ProductBacklogRepository productBacklogRepository;
    private final UserStoryRepository userStoryRepository;

    @Override
    public SprintBacklogOutputDTO creerSprintBacklog(SprintBacklogInputDTO sprintBacklogInputDTO) {
        // Récupérer le ProductBacklog
        ProductBacklog productBacklog = productBacklogRepository.findById(sprintBacklogInputDTO.getProductBacklogId())
                .orElseThrow(() -> new RuntimeException("ProductBacklog non trouvé"));

        // Créer le SprintBacklog
        SprintBacklog sprintBacklog = new SprintBacklog();
        sprintBacklog.setNom(sprintBacklogInputDTO.getNom());
        sprintBacklog.setDateDebut(sprintBacklogInputDTO.getDateDebut());
        sprintBacklog.setDateFin(sprintBacklogInputDTO.getDateFin());
        sprintBacklog.setProductBacklog(productBacklog);

        // Sauvegarder le SprintBacklog
        SprintBacklog savedSprintBacklog = sprintBacklogRepository.save(sprintBacklog);

        // Retourner le SprintBacklog en tant que DTO
        return SprintBacklogOutputDTO.builder()
                .id(savedSprintBacklog.getId())
                .nom(savedSprintBacklog.getNom())
                .dateDebut(savedSprintBacklog.getDateDebut())
                .dateFin(savedSprintBacklog.getDateFin())
                .productBacklogId(savedSprintBacklog.getProductBacklog().getId())
                .build();
    }

    @Override
    public SprintBacklogOutputDTO ajouterUserStoryAuSprint(Long sprintBacklogId, Long userStoryId) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(sprintBacklogId)
                .orElseThrow(() -> new RuntimeException("Sprint Backlog non trouvé"));

        UserStory userStory = userStoryRepository.findById(userStoryId)
                .orElseThrow(() -> new RuntimeException("User Story non trouvée"));

        // Ajouter la UserStory au SprintBacklog
        sprintBacklog.ajouterUserStory(userStory);
        sprintBacklogRepository.save(sprintBacklog);

        // Retourner le SprintBacklog après ajout de la UserStory
        return SprintBacklogOutputDTO.builder()
                .id(sprintBacklog.getId())
                .nom(sprintBacklog.getNom())
                .dateDebut(sprintBacklog.getDateDebut())
                .dateFin(sprintBacklog.getDateFin())
                .productBacklogId(sprintBacklog.getProductBacklog().getId())
                .build();
    }

    @Override
    public List<SprintBacklogOutputDTO> getAllSprintBacklogs() {
        List<SprintBacklog> sprintBacklogs = sprintBacklogRepository.findAll();
        return sprintBacklogs.stream()
                .map(sprintBacklog -> SprintBacklogOutputDTO.builder()
                        .id(sprintBacklog.getId())
                        .nom(sprintBacklog.getNom())
                        .dateDebut(sprintBacklog.getDateDebut())
                        .dateFin(sprintBacklog.getDateFin())
                        .productBacklogId(sprintBacklog.getProductBacklog().getId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SprintBacklogOutputDTO> getSprintBacklogById(Long id) {
        Optional<SprintBacklog> sprintBacklog = sprintBacklogRepository.findById(id);
        return sprintBacklog.map(springBacklogEntity ->
                SprintBacklogOutputDTO.builder()
                        .id(springBacklogEntity.getId())
                        .nom(springBacklogEntity.getNom())
                        .dateDebut(springBacklogEntity.getDateDebut())
                        .dateFin(springBacklogEntity.getDateFin())
                        .productBacklogId(springBacklogEntity.getProductBacklog().getId())
                        .build());
    }

    @Override
    public SprintBacklogOutputDTO updateSprintBacklog(Long id, SprintBacklogInputDTO sprintBacklogInputDTO) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sprint Backlog non trouvé"));

        sprintBacklog.setNom(sprintBacklogInputDTO.getNom());
        sprintBacklog.setDateDebut(sprintBacklogInputDTO.getDateDebut());
        sprintBacklog.setDateFin(sprintBacklogInputDTO.getDateFin());

        // Mettre à jour le ProductBacklog
        ProductBacklog productBacklog = productBacklogRepository.findById(sprintBacklogInputDTO.getProductBacklogId())
                .orElseThrow(() -> new RuntimeException("ProductBacklog non trouvé"));
        sprintBacklog.setProductBacklog(productBacklog);

        SprintBacklog updatedSprintBacklog = sprintBacklogRepository.save(sprintBacklog);

        return SprintBacklogOutputDTO.builder()
                .id(updatedSprintBacklog.getId())
                .nom(updatedSprintBacklog.getNom())
                .dateDebut(updatedSprintBacklog.getDateDebut())
                .dateFin(updatedSprintBacklog.getDateFin())
                .productBacklogId(updatedSprintBacklog.getProductBacklog().getId())
                .build();
    }
    @Override
    public List<UserStoryOutputDTO> getUserStoriesBySprintBacklog(Long sprintBacklogId) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(sprintBacklogId)
                .orElseThrow(() -> new RuntimeException("Sprint Backlog non trouvé"));

        List<UserStory> userStories = sprintBacklog.getUserStories();

        return userStories.stream()
                .map(userStory -> UserStoryOutputDTO.builder()
                        .id(userStory.getId())
                        .title(userStory.getTitle())
                        .description(userStory.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSprintBacklog(Long id) {
        SprintBacklog sprintBacklog = sprintBacklogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sprint Backlog non trouvé"));

        // Supprimer toutes les UserStories associées
        sprintBacklog.getUserStories().forEach(userStory -> userStory.setSprintBacklog(null));  // Remove references

        // Supprimer le SprintBacklog
        sprintBacklogRepository.delete(sprintBacklog);
    }
}
