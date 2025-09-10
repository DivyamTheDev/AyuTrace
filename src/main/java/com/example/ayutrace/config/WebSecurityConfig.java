package com.example.ayutrace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(authz -> authz
                // Public endpoints
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/test/**").permitAll()
                .requestMatchers("/provenance/consumer/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                
                // Collection endpoints
                .requestMatchers(HttpMethod.POST, "/collections").hasRole("FARMER")
                .requestMatchers(HttpMethod.GET, "/collections/my-collections").hasRole("FARMER")
                .requestMatchers(HttpMethod.GET, "/collections/all").hasAnyRole("ADMIN", "REGULATOR")
                .requestMatchers(HttpMethod.GET, "/collections/**").hasAnyRole("FARMER", "PROCESSOR", "LAB_TECHNICIAN", "REGULATOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/collections/**/status").hasAnyRole("FARMER", "PROCESSOR", "REGULATOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/collections/**").hasAnyRole("FARMER", "ADMIN")
                
                // Processing endpoints
                .requestMatchers(HttpMethod.POST, "/processing").hasRole("PROCESSOR")
                .requestMatchers(HttpMethod.GET, "/processing/my-processing").hasRole("PROCESSOR")
                .requestMatchers(HttpMethod.GET, "/processing/**").hasAnyRole("PROCESSOR", "LAB_TECHNICIAN", "REGULATOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/processing/**/status").hasAnyRole("PROCESSOR", "REGULATOR", "ADMIN")
                
                // Quality test endpoints
                .requestMatchers(HttpMethod.POST, "/quality-tests").hasRole("LAB_TECHNICIAN")
                .requestMatchers(HttpMethod.GET, "/quality-tests/my-tests").hasRole("LAB_TECHNICIAN")
                .requestMatchers(HttpMethod.GET, "/quality-tests/**").hasAnyRole("LAB_TECHNICIAN", "REGULATOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/quality-tests/**/status").hasAnyRole("LAB_TECHNICIAN", "REGULATOR", "ADMIN")
                
                // Provenance endpoints
                .requestMatchers(HttpMethod.GET, "/provenance/**").hasAnyRole("FARMER", "PROCESSOR", "LAB_TECHNICIAN", "REGULATOR", "ADMIN", "CONSUMER")
                
                // Admin endpoints
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/users/all").hasAnyRole("ADMIN", "REGULATOR")
                .requestMatchers("/statistics/**").hasAnyRole("REGULATOR", "ADMIN")
                
                // Any other request needs authentication
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        // Add JWT filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow React development server and production builds
        configuration.setAllowedOriginPatterns(List.of(
            "http://localhost:*",
            "http://127.0.0.1:*",
            "https://localhost:*",
            "https://127.0.0.1:*",
            "*" // Allow all origins for development
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of(
            "Authorization",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));
        configuration.setExposedHeaders(List.of(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials"
        ));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // Cache preflight for 1 hour
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
