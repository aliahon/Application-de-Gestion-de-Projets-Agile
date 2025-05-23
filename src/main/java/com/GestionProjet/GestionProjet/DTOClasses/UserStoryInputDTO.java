package com.GestionProjet.GestionProjet.DTOClasses;
import com.GestionProjet.GestionProjet.enumeration.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStoryInputDTO {
    @NotBlank(message="You must enter the user story title")
    @NotNull(message="The user story title cannot be null")
    public String title;
    public String description;
    public Priority priority;
}
