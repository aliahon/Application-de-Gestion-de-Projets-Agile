package com.GestionProjet.GestionProjet.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projet")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductBacklog> productBacklogs;


    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", productBacklogsCount=" + (productBacklogs != null ? productBacklogs.size() : 0) +
                '}';
    }
}