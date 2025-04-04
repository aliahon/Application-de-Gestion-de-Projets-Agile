package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.UserStoryOutputDTO;
import com.GestionProjet.GestionProjet.Entities.UserStory;

import java.util.List;

public interface UserStoryService {
    List<UserStoryOutputDTO> getAllUserStories();
    UserStoryOutputDTO getUserStoryById(Long id);
    UserStoryOutputDTO createUserStory(UserStory userStory);
    UserStoryOutputDTO updateUserStory(Long id, UserStoryOutputDTO dto);
    void deleteUserStory(Long id);
}
