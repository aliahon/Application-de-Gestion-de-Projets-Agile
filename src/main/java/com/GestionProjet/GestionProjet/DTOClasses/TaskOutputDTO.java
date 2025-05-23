package com.GestionProjet.GestionProjet.DTOClasses;

import com.GestionProjet.GestionProjet.enumeration.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskOutputDTO {
    @NotNull(message = "L'id' de la tâche ne peut pas être null.")
    private Long id; // ID generated by the server

    private String title;
    private String description;
    private Status status;

    private Long userStoryId; // The ID of the associated UserStory
}
