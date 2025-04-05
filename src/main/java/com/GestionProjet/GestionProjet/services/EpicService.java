package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.Entities.Epic;

import java.util.List;
import java.util.Optional;

public interface EpicService {
    List<Epic> getAllEpics();
    Optional<Epic> getEpicById(Long id);
    Epic createEpic(Epic epic);
    Epic updateEpic(Long id, Epic epic);
    void deleteEpic(Long id);
}
