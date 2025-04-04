package com.GestionProjet.GestionProjet.Repositories;

import com.GestionProjet.GestionProjet.Entities.Epic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpicRepository extends JpaRepository<Epic, Long> {
}
