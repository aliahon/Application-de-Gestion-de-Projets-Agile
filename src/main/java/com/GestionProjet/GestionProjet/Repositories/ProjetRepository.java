package com.GestionProjet.GestionProjet.Repositories;

import com.GestionProjet.GestionProjet.Entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetRepository extends JpaRepository<Projet, Long> {}
