package com.GestionProjet.GestionProjet.DTOClasses;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductBacklogDTO {
    public String nom;
    public String techniquePriorisation;
    public int sprintDuration;
}
