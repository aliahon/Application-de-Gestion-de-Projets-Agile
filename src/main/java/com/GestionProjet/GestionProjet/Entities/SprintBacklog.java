package com.GestionProjet.GestionProjet.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sprint_backlog")
public class SprintBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @OneToMany(mappedBy = "sprintBacklog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserStory> userStories;

    // Méthode pour ajouter une UserStory au Sprint Backlog
    public void ajouterUserStory(UserStory userStory) {
        userStories.add(userStory);
        userStory.setSprintBacklog(this);
    }

    @ManyToOne
    @JoinColumn(name = "product_backlog_id")
    private ProductBacklog productBacklog;
}

