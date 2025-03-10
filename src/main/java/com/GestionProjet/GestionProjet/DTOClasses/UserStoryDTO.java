package com.GestionProjet.GestionProjet.DTOClasses;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStoryDTO {
    public String title;
    public String description;
}
