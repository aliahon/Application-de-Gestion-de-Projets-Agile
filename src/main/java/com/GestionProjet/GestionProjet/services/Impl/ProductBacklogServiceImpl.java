package com.GestionProjet.GestionProjet.Services.Impl;

import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogDTO;
import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import com.GestionProjet.GestionProjet.Repositories.ProductBacklogRepository;
import com.GestionProjet.GestionProjet.Repositories.ProjetRepository;
import com.GestionProjet.GestionProjet.Services.ProductBacklogService;
import com.GestionProjet.GestionProjet.enumeration.TechniquePriorisation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductBacklogServiceImpl implements ProductBacklogService {
    private final ProductBacklogRepository repository;
    private final ProjetRepository projetRepository;

    private ProductBacklogDTO convertToDto(ProductBacklog backlog) {
        return ProductBacklogDTO.builder()
                .id(backlog.getId())
                .nom(backlog.getNom())
                .techniquePriorisation(backlog.getTechniquePriorisation())
                .projet_id(backlog.getProjet().getProjet_id())
                .build();
    }

    @Override
    public List<ProductBacklogDTO> getAllProductBacklogs() {
        List<ProductBacklog> backlogs = repository.findAll();
        List<ProductBacklogDTO> backlogDTOs = new ArrayList<>();

        for (ProductBacklog backlog : backlogs) {
            backlogDTOs.add(convertToDto(backlog));
        }

        return backlogDTOs;
    }

    @Override
    public ProductBacklogDTO getProductBacklogById(Long id) {
        ProductBacklog backlog = repository.findById(id).orElse(null);
        return (backlog != null) ? convertToDto(backlog) : null;
    }

    @Override
    public ProductBacklogDTO createProductBacklog(ProductBacklogCreateDTO backlog) {

        if (backlog.getNom() == null || backlog.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Product Backlog name cannot be empty");
        }
        if (backlog.getTechniquePriorisation() == null) {
            throw new IllegalArgumentException("Technique Priorisation must be provided");
        }

        ProductBacklog savedBacklog = ProductBacklog.builder()
                .nom(backlog.getNom())
                .techniquePriorisation(TechniquePriorisation.valueOf(backlog.getTechniquePriorisation()))
                .projet(projetRepository.findById(backlog.getProjet_id()).orElse(null))
                .build();

        savedBacklog = repository.save(savedBacklog);
        return convertToDto(savedBacklog);
    }

    @Override
    public ProductBacklogDTO updateProductBacklog(Long id, ProductBacklogDTO dto) {
        ProductBacklog backlog = repository.findById(id).orElse(null);

        if (backlog != null) {
            backlog.setNom(dto.getNom());
            backlog.setTechniquePriorisation(dto.getTechniquePriorisation());

            ProductBacklog updatedBacklog = repository.save(backlog);
            return convertToDto(updatedBacklog);
        }
        return null;
    }

    @Override
    public void deleteProductBacklog(Long id) {
        repository.deleteById(id);
    }
}
