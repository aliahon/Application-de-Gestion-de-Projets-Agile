package com.GestionProjet.GestionProjet.DTOClasses;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class JwtRequest implements Serializable {
    private String username;
    private String password;
}