package com.GestionProjet.GestionProjet.Services;

import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogDTO;
import com.GestionProjet.GestionProjet.Entities.ProductBacklog;

import java.util.List;
import java.util.Optional;

public interface ProductBacklogService {
    List<ProductBacklogDTO> getAllProductBacklogs();
    ProductBacklogDTO getProductBacklogById(Long id);
    ProductBacklogDTO createProductBacklog(ProductBacklogCreateDTO backlog);
    ProductBacklogDTO updateProductBacklog(Long id, ProductBacklogDTO dto);
    void deleteProductBacklog(Long id);
}
