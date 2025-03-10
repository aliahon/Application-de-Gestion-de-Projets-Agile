package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogDTO;
import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import com.GestionProjet.GestionProjet.Entities.TechniquePriorisation;
import com.GestionProjet.GestionProjet.Repositories.ProductBacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductBacklogService implements ProductBacklogServiceInterface {
    private final ProductBacklogRepository repository;

    @Autowired
    public ProductBacklogService(ProductBacklogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductBacklog> getAllProductBacklogs() {
        return repository.findAll();
    }

    @Override
    public ProductBacklog getProductBacklogById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ProductBacklog createProductBacklog(ProductBacklog backlog) {
        if (backlog.getNom() == null || backlog.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Product Backlog name cannot be empty");
        }
        if (backlog.getTechniquePriorisation() == null) {
            throw new IllegalArgumentException("Technique Priorisation must be provided");
        }
        if (backlog.getSprintDuration() <= 0) {
            throw new IllegalArgumentException("Sprint duration must be greater than zero");
        }
        return repository.save(backlog);
    }

    @Override
    public ProductBacklog updateProductBacklog(Long id, ProductBacklogDTO dto) {
        Optional<ProductBacklog> existingBacklog = repository.findById(id);
        if (existingBacklog.isPresent()) {
            ProductBacklog backlog = existingBacklog.get();
            if (dto.nom != null) backlog.setNom(dto.nom);
            if (dto.techniquePriorisation != null) backlog.setTechniquePriorisation(TechniquePriorisation.valueOf(dto.techniquePriorisation));
            if (dto.sprintDuration > 0) backlog.setSprintDuration(dto.sprintDuration);
            return repository.save(backlog);
        }
        return null;
    }

    @Override
    public void deleteProductBacklog(Long id) {
        repository.deleteById(id);
    }
}
