package com.GestionProjet.GestionProjet.DTOClasses;

import com.GestionProjet.GestionProjet.enumeration.Status;
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
public class TaskInputDTO {

    @NotNull(message = "Le titre de la tâche ne peut pas être null.")
    @Size(min = 1, max = 100, message = "Le titre de la tâche doit avoir entre 1 et 100 caractères.")
    private String title;

    @Size(max = 500, message = "La description de la tâche ne peut pas dépasser 500 caractères.")
    private String description;

    @NotNull(message = "Le statut de la tâche ne peut pas être null.")
    private Status status;

    @NotNull(message = "L'ID de la User Story associée à la tâche ne peut pas être null.")
    private Long userStoryId; // L'ID de la UserStory associée
}
