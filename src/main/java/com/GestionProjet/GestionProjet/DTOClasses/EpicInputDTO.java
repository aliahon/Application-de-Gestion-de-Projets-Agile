package com.GestionProjet.GestionProjet.DTOClasses;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpicInputDTO {

    @NotNull(message = "Le nom de l'Epic ne peut pas être null.")
    @Size(min = 1, max = 100, message = "Le nom de l'Epic doit avoir entre 1 et 100 caractères.")
    private String nom;

    @Size(max = 500, message = "La description de l'Epic ne peut pas dépasser 500 caractères.")
    private String description;

    @NotNull(message = "Le ProductBacklog associé à l'Epic ne peut pas être null.")
    private Long productBacklogId; // L'ID du ProductBacklog associé
}
