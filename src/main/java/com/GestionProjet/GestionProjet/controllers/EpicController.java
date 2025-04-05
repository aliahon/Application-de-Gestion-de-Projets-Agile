package com.GestionProjet.GestionProjet.Controllers;

import com.GestionProjet.GestionProjet.Entities.Epic;
import com.GestionProjet.GestionProjet.Services.EpicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/epics")
@RequiredArgsConstructor
public class EpicController {

    private final EpicService epicService;

    @GetMapping
    public ResponseEntity<List<Epic>> getAllEpics() {
        return ResponseEntity.ok(epicService.getAllEpics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Epic> getEpicById(@PathVariable Long id) {
        return epicService.getEpicById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Epic> createEpic(@RequestBody Epic epic) {
        return ResponseEntity.ok(epicService.createEpic(epic));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Epic> updateEpic(@PathVariable Long id, @RequestBody Epic epic) {
        try {
            Epic updatedEpic = epicService.updateEpic(id, epic);
            return ResponseEntity.ok(updatedEpic);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpic(@PathVariable Long id) {
        epicService.deleteEpic(id);
        return ResponseEntity.noContent().build();
    }
}
