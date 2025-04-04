package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.UserStoryOutputDTO;
import com.GestionProjet.GestionProjet.Entities.UserStory;

import java.util.List;

public interface UserStoryService {
    List<UserStory> getAllUserStories();
    UserStory getUserStoryById(Long id);
    UserStory createUserStory(UserStory userStory);
    UserStory updateUserStory(Long id, UserStoryOutputDTO dto);
    void deleteUserStory(Long id);
}
