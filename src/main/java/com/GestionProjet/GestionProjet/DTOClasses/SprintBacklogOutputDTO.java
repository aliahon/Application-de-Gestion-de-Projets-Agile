package com.GestionProjet.GestionProjet.DTOClasses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintBacklogOutputDTO {

    private Long id; // L'ID généré par le serveur

    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    @Builder.Default
    private List<UserStoryOutputDTO> userStories = new ArrayList<>();
    private Long productBacklogId; // L'ID du ProductBacklog associé
}
