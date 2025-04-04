package com.GestionProjet.GestionProjet.Services.Impl;

import com.GestionProjet.GestionProjet.DTOClasses.UserStoryOutputDTO;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import com.GestionProjet.GestionProjet.Services.UserStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStoryServiceImpl implements UserStoryService {
    private final UserStoryRepository repo;
    
    @Override
    public List<UserStory> getAllUserStories(){
        return repo.findAll();
    }

    @Override
    public UserStory getUserStoryById(Long id){
        return repo.findById(id).orElse(null);
    }

    @Override
    public UserStory createUserStory(UserStory userStory){
        if (userStory.getTitle() == null || userStory.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("User Story title cannot be empty");
        }
        if (userStory.getDescription() == null || userStory.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("User Story description cannot be empty");
        }
        return repo.save(userStory);
    }

    @Override
    public UserStory updateUserStory(Long id, UserStoryOutputDTO dto){
        Optional<UserStory> existingUserStory = repo.findById(id);
        if (existingUserStory.isPresent()) {
            UserStory userStory = existingUserStory.get();
            if (dto.title != null) userStory.setTitle(dto.title);
            if (dto.description != null) userStory.setDescription(dto.description);
            return repo.save(userStory);
        }
        return null;
    }

    @Override
    public void deleteUserStory(Long id){
        repo.deleteById(id);
    }
}

