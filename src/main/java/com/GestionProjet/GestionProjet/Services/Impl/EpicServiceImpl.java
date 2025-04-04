package com.GestionProjet.GestionProjet.Services.Impl;

import com.GestionProjet.GestionProjet.Entities.Epic;
import com.GestionProjet.GestionProjet.Repositories.EpicRepository;
import com.GestionProjet.GestionProjet.Services.EpicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EpicServiceImpl implements EpicService {

    private final EpicRepository epicRepository;

    @Override
    public List<Epic> getAllEpics() {
        return epicRepository.findAll();
    }

    @Override
    public Optional<Epic> getEpicById(Long id) {
        return epicRepository.findById(id);
    }

    @Override
    public Epic createEpic(Epic epic) {
        return epicRepository.save(epic);
    }

    @Override
    public Epic updateEpic(Long id, Epic updatedEpic) {
        Optional<Epic> optionalEpic = epicRepository.findById(id);
        if (optionalEpic.isPresent()) {
            Epic epic = optionalEpic.get();
            epic.setProductBacklog(updatedEpic.getProductBacklog());
            return epicRepository.save(epic);
        }
        throw new RuntimeException("Epic not found with id: " + id);
    }

    @Override
    public void deleteEpic(Long id) {
        epicRepository.deleteById(id);
    }
}
