package com.GestionProjet.GestionProjet.DTOClasses;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOutput {

    private String token;
    private String message;
}
