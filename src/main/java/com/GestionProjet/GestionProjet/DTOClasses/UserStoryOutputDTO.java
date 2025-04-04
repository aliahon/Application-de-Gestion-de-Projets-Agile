package com.GestionProjet.GestionProjet.DTOClasses;

import com.GestionProjet.GestionProjet.enumeration.Priority;
import com.GestionProjet.GestionProjet.enumeration.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStoryOutputDTO {
    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private Long epicId;             // Optional: ID of associated Epic
    private Long productBacklogId;   // Optional: ID of associated Product Backlog
    private Long sprintBacklogId;    // Optional: ID of associated Sprint Backlog
}
