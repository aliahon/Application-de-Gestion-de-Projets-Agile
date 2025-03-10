package com.GestionProjet.GestionProjet.DTOClasses;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjetDTO {
    public String nom;
    public String description;

}
