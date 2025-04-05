package com.GestionProjet.GestionProjet.DTOClasses;

import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SprintBacklogInputDTO {

    @NotNull(message = "Le nom du Sprint Backlog ne peut pas être null.")
    @Size(min = 1, max = 100, message = "Le nom du Sprint Backlog doit avoir entre 1 et 100 caractères.")
    private String nom;

    @NotNull(message = "La date de début ne peut pas être null.")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin ne peut pas être null.")
    private LocalDate dateFin;

    private Long productBacklogId;

}
