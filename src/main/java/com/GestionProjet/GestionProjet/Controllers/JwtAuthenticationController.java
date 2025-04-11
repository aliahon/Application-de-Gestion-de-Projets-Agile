package com.GestionProjet.GestionProjet.Controllers;

import com.GestionProjet.GestionProjet.DTOClasses.JwtRequest;
import com.GestionProjet.GestionProjet.DTOClasses.JwtResponse;
import com.GestionProjet.GestionProjet.DTOClasses.UserCreateDTO;
import com.GestionProjet.GestionProjet.DTOClasses.UserDTO;
import com.GestionProjet.GestionProjet.Services.Impl.JwtTokenUtil;
import com.GestionProjet.GestionProjet.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.authentication.BadCredentialsException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest)
            throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(JwtResponse.builder().jwttoken(token).build());
    }
    // 📝 REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserCreateDTO registerRequest) {
        UserDTO newUser = userService.createUser(registerRequest);
        return ResponseEntity.ok("User registered successfully with role: " + newUser.getRole());
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (DisabledException e) {
            throw new Exception("UTILISATEUR_DÉSACTIVÉ", e);
        }
        catch (BadCredentialsException e) {
            throw new Exception("IDENTIFIANTS_INVALIDES", e);
        }
    }


}

