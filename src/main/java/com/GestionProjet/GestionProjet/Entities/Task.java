package com.GestionProjet.GestionProjet.Entities;

import com.GestionProjet.GestionProjet.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Status status;

    @ManyToOne
    @JoinColumn(name = "us_id")
    private UserStory userstory;

}
