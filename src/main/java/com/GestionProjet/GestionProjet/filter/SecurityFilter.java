package com.GestionProjet.GestionProjet.filter;

import com.GestionProjet.GestionProjet.Services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService util;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip JWT check for Swagger and auth paths
        if (path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/configuration") ||
                path.startsWith("/webjars") ||
                path.startsWith("/user")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Reading Token from Authorization Header
        String token= request.getHeader("Authorization");
        if(token !=null) {
            String username= util.getSubject(token);
            //if username is not null & Context Authentication must be null
            if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
                UserDetails user= userDetailsService.loadUserByUsername(username);
                boolean isValid=util.isValidToken(token, user.getUsername());
                if(isValid) {
                    UsernamePasswordAuthenticationToken authToken=
                            new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
            }
        }
        filterChain.doFilter(request, response);
    }

}