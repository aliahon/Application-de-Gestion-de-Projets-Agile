package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.ProjetCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.ProjetDTO;
import com.GestionProjet.GestionProjet.Entities.Projet;

import java.util.List;
import java.util.Optional;

public interface ProjetService {
    List<ProjetDTO> getAllProjets();
    ProjetDTO getProjetById(Long id);
    ProjetDTO createProjet(ProjetCreateDTO projet);
    ProjetDTO updateProjet(Long id, ProjetDTO dto);
    void deleteProjet(Long id);
}