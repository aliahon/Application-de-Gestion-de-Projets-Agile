package com.GestionProjet.GestionProjet.DTOClasses;
import com.GestionProjet.GestionProjet.enumeration.TechniquePriorisation;
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
public class ProductBacklogDTO {
    @NotNull(message="The product backlog id cannot be null")
    public Long id;
    @NotBlank(message="You must enter the product backlog name")
    @NotNull(message="The product backlog name cannot be null")
    public String nom;
    @NotNull(message="TechniquePriorisation cannot be null")
    public TechniquePriorisation techniquePriorisation;
    @NotNull(message="projet_id cannot be null")
    public Long projet_id;
}
