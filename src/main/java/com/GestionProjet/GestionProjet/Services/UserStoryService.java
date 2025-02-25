package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStoryService {
    private final UserStoryRepository repo;

    @Autowired
    public UserStoryService(UserStoryRepository userStoryRepository) {
        this.repo = userStoryRepository;
    }

    public List<UserStory> getAllUserStories(){
        return repo.findAll();
    }

    public UserStory getUserStoryById(Long id){
        return repo.findById(id).orElse(null);
    }

    public UserStory createUserStory(UserStory userStory){
        return repo.save(userStory);
    }

    public UserStory updateUserStory(Long id,UserStory userStory){
        if(repo.existsById(id)){
            userStory.setId(id);
            return repo.save(userStory);
        }
        return null;
    }

    public void deleteUserStory(Long id){
        repo.deleteById(id);
    }

}
