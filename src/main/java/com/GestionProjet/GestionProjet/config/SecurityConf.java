package com.GestionProjet.GestionProjet.config;

import com.GestionProjet.GestionProjet.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConf {
    private final UnAuthorizedUserAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    // Public endpoints
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/webjars/**"
                    ).permitAll();

                    //UserStoryController
                    auth.requestMatchers(HttpMethod.GET, "/user-stories/**")
                            .hasAnyRole("PRODUCT_OWNER", "DEVELOPPEUR");
                    auth.requestMatchers(HttpMethod.POST, "/user-stories/**")
                            .hasAnyRole("PRODUCT_OWNER", "SCRUM_MASTER");
                    auth.requestMatchers(HttpMethod.PUT, "/user-stories/**")
                            .hasAnyRole("PRODUCT_OWNER", "SCRUM_MASTER");
                    auth.requestMatchers(HttpMethod.DELETE, "/user-stories/**")
                            .hasAnyRole("PRODUCT_OWNER", "SCRUM_MASTER");

                    //TaskController
                    auth.requestMatchers(HttpMethod.POST, "/tasks/**")
                            .hasRole("SCRUM_MASTER");
                    auth.requestMatchers(HttpMethod.DELETE, "/tasks/**")
                            .hasRole("SCRUM_MASTER");
                    auth.requestMatchers(HttpMethod.GET, "/tasks/**")
                            .hasAnyRole("SCRUM_MASTER", "DEVELOPPEUR");
                    auth.requestMatchers(HttpMethod.PUT, "/tasks/**")
                            .hasAnyRole("SCRUM_MASTER", "DEVELOPPEUR");

                    //SprintBacklogController
                    auth.requestMatchers(HttpMethod.GET, "/sprintBacklogs/**")
                            .permitAll();
                    auth.requestMatchers("/sprintBacklogs/**")
                            .hasRole("SCRUM_MASTER");

                    //ProjetController
                    auth.requestMatchers(HttpMethod.GET, "/projets/**")
                            .hasAnyRole("SCRUM_MASTER", "DEVELOPPEUR", "PRODUCT_OWNER");
                    auth.requestMatchers("/projets/**")
                            .hasRole("PRODUCT_OWNER");

                    //ProductBacklogController
                    auth.requestMatchers(HttpMethod.GET, "/product-backlogs/**")
                            .hasAnyRole("SCRUM_MASTER", "DEVELOPPEUR");
                    auth.requestMatchers("/product-backlogs/**")
                            .hasRole("PRODUCT_OWNER");

                    //EpicController
                    auth.requestMatchers(HttpMethod.GET, "/epics/**")
                            .permitAll();
                    auth.requestMatchers("/epics/**")
                            .hasRole("PRODUCT_OWNER");

                    // All other requests
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

