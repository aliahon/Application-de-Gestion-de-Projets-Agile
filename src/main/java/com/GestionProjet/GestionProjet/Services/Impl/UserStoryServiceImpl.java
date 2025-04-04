package com.GestionProjet.GestionProjet.Services.Impl;

import com.GestionProjet.GestionProjet.DTOClasses.UserStoryOutputDTO;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Repositories.UserStoryRepository;
import com.GestionProjet.GestionProjet.Services.UserStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserStoryServiceImpl implements UserStoryService {

    private final UserStoryRepository repo;

    @Override
    public List<UserStoryOutputDTO> getAllUserStories() {
        return repo.findAll().stream()
                .map(this::mapToOutputDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserStoryOutputDTO getUserStoryById(Long id) {
        return repo.findById(id)
                .map(this::mapToOutputDTO)
                .orElse(null);
    }

    @Override
    public UserStoryOutputDTO createUserStory(UserStory userStory) {
        if (userStory.getTitle() == null || userStory.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("User Story title cannot be empty");
        }
        if (userStory.getDescription() == null || userStory.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("User Story description cannot be empty");
        }

        UserStory saved = repo.save(userStory);
        return mapToOutputDTO(saved);
    }

    @Override
    public UserStoryOutputDTO updateUserStory(Long id, UserStoryOutputDTO dto) {
        Optional<UserStory> existingUserStory = repo.findById(id);
        if (existingUserStory.isPresent()) {
            UserStory userStory = existingUserStory.get();

            if (dto.getTitle() != null) userStory.setTitle(dto.getTitle());
            if (dto.getDescription() != null) userStory.setDescription(dto.getDescription());
            if (dto.getPriority() != null) userStory.setPriority(dto.getPriority());
            if (dto.getStatus() != null) userStory.setStatus(dto.getStatus());

            // You could add updates for epic, productBacklog, sprintBacklog here if needed
            UserStory updated = repo.save(userStory);
            return mapToOutputDTO(updated);
        }
        return null;
    }

    @Override
    public void deleteUserStory(Long id) {
        repo.deleteById(id);
    }

    private UserStoryOutputDTO mapToOutputDTO(UserStory userStory) {
        return UserStoryOutputDTO.builder()
                .id(userStory.getId())
                .title(userStory.getTitle())
                .description(userStory.getDescription())
                .priority(userStory.getPriority())
                .status(userStory.getStatus())
                .epicId(userStory.getEpic() != null ? userStory.getEpic().getId() : null)
                .productBacklogId(userStory.getProductBacklog() != null ? userStory.getProductBacklog().getId() : null)
                .sprintBacklogId(userStory.getSprintBacklog() != null ? userStory.getSprintBacklog().getId() : null)
                .build();
    }
}
