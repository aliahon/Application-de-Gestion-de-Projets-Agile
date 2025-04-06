package com.GestionProjet.GestionProjet.Controllers;

import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.ProductBacklogDTO;
import com.GestionProjet.GestionProjet.Entities.ProductBacklog;
import com.GestionProjet.GestionProjet.Services.ProductBacklogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-backlogs")
@RequiredArgsConstructor
public class ProductBacklogController {

    private final ProductBacklogService productBacklogService;


    // Create a Product Backlog
    @PostMapping
    public ProductBacklogDTO createProductBacklog(@Valid @RequestBody ProductBacklogCreateDTO backlog) {
        return productBacklogService.createProductBacklog(backlog);
    }

    // Update a Product Backlog
    @PutMapping("/{id}")
    public ProductBacklogDTO updateProductBacklog(@PathVariable Long id, @RequestBody ProductBacklogDTO backlogDTO) {
        return productBacklogService.updateProductBacklog(id, backlogDTO);
    }

    // Delete a Product Backlog
    @DeleteMapping("/{id}")
    public void deleteProductBacklog(@PathVariable Long id) {
        productBacklogService.deleteProductBacklog(id);
    }

    // Retrieve a Product Backlog by ID
    @GetMapping("/{id}")
    public ProductBacklogDTO getProductBacklogById(@PathVariable Long id) {
        return productBacklogService.getProductBacklogById(id);
    }

    // List all Product Backlogs
    @GetMapping
    public List<ProductBacklogDTO> getAllProductBacklogs() {
        return productBacklogService.getAllProductBacklogs();
    }
}
