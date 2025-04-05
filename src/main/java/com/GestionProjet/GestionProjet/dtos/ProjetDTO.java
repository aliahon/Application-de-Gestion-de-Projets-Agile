package com.GestionProjet.GestionProjet.DTOClasses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjetDTO {
    @NotNull(message="The project id cannot be null")
    public Long id;
    @NotBlank(message="You must enter the project name")
    @NotNull(message="The project name cannot be null")
    public String nom;
    public String description;

}
