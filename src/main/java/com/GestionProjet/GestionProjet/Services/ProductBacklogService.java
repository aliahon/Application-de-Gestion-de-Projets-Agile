package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import com.GestionProjet.GestionProjet.Repositories.ProductBacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBacklogService {
    private final ProductBacklogRepository repository;

    @Autowired
    public ProductBacklogService(ProductBacklogRepository repository) {
        this.repository = repository;
    }

    public List<ProductBacklog> getAllProductBacklogs() {
        return repository.findAll();
    }

    public ProductBacklog getProductBacklogById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ProductBacklog createProductBacklog(ProductBacklog backlog) {
        return repository.save(backlog);
    }

    public ProductBacklog updateProductBacklog(Long id, ProductBacklog updatedBacklog) {
        if (repository.existsById(id)) {
            updatedBacklog.setId(id);
            return repository.save(updatedBacklog);
        }
        return null;
    }

    public void deleteProductBacklog(Long id) {
        repository.deleteById(id);
    }
}
