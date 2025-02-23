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
}
