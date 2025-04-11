package com.GestionProjet.GestionProjet.DTOClasses;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class JwtResponse implements Serializable {
    private final String jwttoken;
}

