
package com.GestionProjet.GestionProjet.Entities;

import com.GestionProjet.GestionProjet.enumeration.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Table(name="user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    @GeneratedValue
    @Column(name = "user_id")
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


    @ManyToMany(mappedBy = "users")
    private List<Projet> projets;

}