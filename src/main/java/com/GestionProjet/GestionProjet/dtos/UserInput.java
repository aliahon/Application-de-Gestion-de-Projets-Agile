package com.GestionProjet.GestionProjet.DTOClasses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInput {

    private String username;
    private String password;
}