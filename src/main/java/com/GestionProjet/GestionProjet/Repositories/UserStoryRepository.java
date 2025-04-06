package com.GestionProjet.GestionProjet.Repositories;

import com.GestionProjet.GestionProjet.Entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
    List<UserStory> findByProductBacklogId(Long productBacklogId);
    List<UserStory> findBySprintBacklogId(Long sprintBacklogId);
    List<UserStory> findByEpicId(Long epicId);
}
