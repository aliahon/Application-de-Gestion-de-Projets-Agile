package com.GestionProjet.GestionProjet.DTOClasses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStoryOutputDTO {
    @NotNull(message="The user story id cannot be null")
    public Long id;
    @NotBlank(message="You must enter the user story title")
    @NotNull(message="The user story title cannot be null")
    public String title;
    public String description;
}
