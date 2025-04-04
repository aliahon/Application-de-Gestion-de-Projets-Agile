package com.GestionProjet.GestionProjet.Controllers;

import com.GestionProjet.GestionProjet.DTOClasses.UserStoryOutputDTO;
import com.GestionProjet.GestionProjet.Entities.UserStory;
import com.GestionProjet.GestionProjet.Services.UserStoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-stories")
@RequiredArgsConstructor
public class UserStoryController {

    private final UserStoryService userStoryService;

    @GetMapping
    public ResponseEntity<List<UserStoryOutputDTO>> getAllUserStories() {
        List<UserStoryOutputDTO> userStories = userStoryService.getAllUserStories();
        return ResponseEntity.ok(userStories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStoryOutputDTO> getUserStoryById(@PathVariable Long id) {
        UserStoryOutputDTO userStory = userStoryService.getUserStoryById(id);
        if (userStory != null) {
            return ResponseEntity.ok(userStory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserStoryOutputDTO> createUserStory(@RequestBody UserStory userStory) {
        try {
            UserStoryOutputDTO created = userStoryService.createUserStory(userStory);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserStoryOutputDTO> updateUserStory(@PathVariable Long id, @RequestBody UserStoryOutputDTO dto) {
        UserStoryOutputDTO updated = userStoryService.updateUserStory(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserStory(@PathVariable Long id) {
        userStoryService.deleteUserStory(id);
        return ResponseEntity.noContent().build();
    }
}
