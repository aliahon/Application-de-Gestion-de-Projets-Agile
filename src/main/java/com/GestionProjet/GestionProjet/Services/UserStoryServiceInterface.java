package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.UserStoryDTO;
import com.GestionProjet.GestionProjet.Entities.UserStory;

import java.util.List;

public interface UserStoryServiceInterface {
    List<UserStory> getAllUserStories();
    UserStory getUserStoryById(Long id);
    UserStory createUserStory(UserStory userStory);
    UserStory updateUserStory(Long id, UserStoryDTO dto);
    void deleteUserStory(Long id);
}
