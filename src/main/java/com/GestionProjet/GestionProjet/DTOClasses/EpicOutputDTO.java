package com.GestionProjet.GestionProjet.DTOClasses;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpicOutputDTO {
    @NotNull(message = "l'id ne peut pas etre nul")
    private Long id;

    private String nom;
    private String description;

    private Long productBacklogId; // L'ID du ProductBacklog associé
}
