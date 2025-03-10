package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogDTO;
import com.GestionProjet.GestionProjet.Entities.ProductBacklog;

import java.util.List;

public interface ProductBacklogServiceInterface {
    List<ProductBacklog> getAllProductBacklogs();
    ProductBacklog getProductBacklogById(Long id);
    ProductBacklog createProductBacklog(ProductBacklog backlog);
    ProductBacklog updateProductBacklog(Long id, ProductBacklogDTO dto);
    void deleteProductBacklog(Long id);
}
