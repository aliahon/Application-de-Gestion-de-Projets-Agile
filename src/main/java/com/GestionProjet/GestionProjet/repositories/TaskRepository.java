package com.GestionProjet.GestionProjet.Repositories;

import com.GestionProjet.GestionProjet.Entities.Task;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}