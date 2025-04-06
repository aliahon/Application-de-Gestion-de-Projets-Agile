
package com.GestionProjet.GestionProjet.Entities;

import com.GestionProjet.GestionProjet.enumeration.Role;
import jakarta.persistence.*;
        import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private String password;

    private String email;

    private Role role;

    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

}