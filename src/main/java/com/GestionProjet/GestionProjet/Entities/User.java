
package com.GestionProjet.GestionProjet.Entities;

import com.GestionProjet.GestionProjet.enumeration.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name="user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String email;

    @Builder.Default
    private boolean active = true;
    @Builder.Default
    private boolean accountNonLocked = true;
    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;

    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

}