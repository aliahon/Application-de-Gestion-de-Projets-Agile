package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.ProjetDTO;
import com.GestionProjet.GestionProjet.Entities.Projet;
import com.GestionProjet.GestionProjet.Repositories.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProjetServiceInterface {
    List<Projet> getAllProjets();
    Projet getProjetById(Long id);
    Projet createProjet(Projet projet);
    Projet updateProjet(Long id, ProjetDTO dto);
    void deleteProjet(Long id);
}