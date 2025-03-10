package com.GestionProjet.GestionProjet.Entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_backlog")

@Builder
public class ProductBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private TechniquePriorisation techniquePriorisation;
    private int sprintDuration;

    @OneToMany(mappedBy = "productBacklog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;

    @OneToMany(mappedBy = "productBacklog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Epic> epics;

    @OneToMany(mappedBy = "productBacklog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserStory> userStories;

    @OneToMany(mappedBy = "productBacklog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SprintBacklog> sprintBacklogs;

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @Override
    public String toString() {
        return "ProductBacklog{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", techniquePriorisation='" + techniquePriorisation + '\'' +
                ", sprintDuration=" + sprintDuration +
                ", userStoriesCount=" + (userStories != null ? userStories.size() : 0) +
                ", projetId=" + (projet != null ? projet.getId() : "null") +
                '}';
    }
}
