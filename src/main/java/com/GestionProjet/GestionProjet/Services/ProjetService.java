package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.ProjetDTO;
import com.GestionProjet.GestionProjet.Entities.Projet;
import com.GestionProjet.GestionProjet.Repositories.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetService implements ProjetServiceInterface {
    private final ProjetRepository repository;

    @Autowired
    public ProjetService(ProjetRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Projet> getAllProjets() {
        return repository.findAll();
    }

    @Override
    public Projet getProjetById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Projet createProjet(Projet projet) {
        if (projet.getNom() == null || projet.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Projet name cannot be empty");
        }
        return repository.save(projet);
    }

    @Override
    public Projet updateProjet(Long id, ProjetDTO dto) {
        Optional<Projet> existingProjet = repository.findById(id);
        if (existingProjet.isPresent()) {
            Projet projet = existingProjet.get();
            if (dto.nom != null && !dto.nom.trim().isEmpty()) projet.setNom(dto.nom);
            if (dto.description != null && !dto.description.trim().isEmpty()) projet.setDescription(dto.description);
            return repository.save(projet);
        }
        return null;
    }

    @Override
    public void deleteProjet(Long id) {
        repository.deleteById(id);
    }
}