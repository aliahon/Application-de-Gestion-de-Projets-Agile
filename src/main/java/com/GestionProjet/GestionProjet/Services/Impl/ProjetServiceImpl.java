package com.GestionProjet.GestionProjet.Services.Impl;

import com.GestionProjet.GestionProjet.DTOClasses.ProjetCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.ProjetDTO;
import com.GestionProjet.GestionProjet.Entities.Projet;
import com.GestionProjet.GestionProjet.Repositories.ProjetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjetServiceImpl implements com.GestionProjet.GestionProjet.Services.ProjetService {
    private final ProjetRepository repository;


    @Override
    public List<ProjetDTO> getAllProjets() {
        List<Projet> projets = repository.findAll();
        List<ProjetDTO> projetDTOs = new ArrayList<>();

        for (Projet projet : projets) {
            projetDTOs.add(convertToDTO(projet));
        }

        return projetDTOs;
    }

    @Override
    public ProjetDTO getProjetById(Long id) {
        Optional<Projet> projet = repository.findById(id);
        if (projet.isPresent()) {
            return convertToDTO(projet.get());
        }
        return null;
    }

    @Override
    public ProjetDTO createProjet(ProjetCreateDTO dto) {
        if (dto.nom == null || dto.nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Projet name cannot be empty");
        }

        Projet projet = Projet.builder()
                .nom(dto.nom)
                .description(dto.description)
                .build();
        Projet savedProjet = repository.save(projet);
        return convertToDTO(savedProjet);
    }

    @Override
    public ProjetDTO updateProjet(Long id, ProjetDTO dto) {
        Optional<Projet> existingProjet = repository.findById(id);
        if (existingProjet.isPresent()) {
            Projet projet = existingProjet.get();
            if (dto.nom != null && !dto.nom.trim().isEmpty()) {
                projet.setNom(dto.nom);
            }
            if (dto.description != null && !dto.description.trim().isEmpty()) {
                projet.setDescription(dto.description);
            }

            Projet updatedProjet = repository.save(projet);
            return convertToDTO(updatedProjet);
        }
        return null;
    }

    @Override
    public void deleteProjet(Long id) {
        repository.deleteById(id);
    }

    // Conversion functions
    private ProjetDTO convertToDTO(Projet projet) {
        return ProjetDTO.builder()
                .id(projet.getProjet_id())
                .nom(projet.getNom())
                .description(projet.getDescription())
                .build();
    }

}
