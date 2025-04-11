package com.GestionProjet.GestionProjet.DTOClasses;

import com.GestionProjet.GestionProjet.enumeration.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
