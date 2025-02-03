package com.g1springboard.ArogyaPolicy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity in development
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/user/register","/dashboard","/policy","/logo.png").permitAll()
                        .requestMatchers("/registration/**", "/js/**", "/css/**", "/img/**", "/user/dashboard","/forgot/**").permitAll()
                        
                        // Allow register & login endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/policy/enroll/confirm/**").permitAll()

                        .anyRequest().authenticated() // Secure all other endpoints
                )
                        .formLogin(form -> form
                        .loginPage("/login") 
                        .loginProcessingUrl("/login")// URL where login POST requests are processed
                        .usernameParameter("email") // Set the email parameter to be used as the username
                        .passwordParameter("password")
                        .successHandler(successHandler())
                        .permitAll()

                )
                .logout(logout -> logout
                .logoutUrl("/logout") // Logout endpoint
                .logoutSuccessUrl("/login") 
                        .clearAuthentication(true) // Clear authentication
                        .permitAll()
                )
                .build(); // Return the configured SecurityFilterChain
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            // Get the "continue" URL parameter
            String continueUrl = request.getParameter("continue");
    
            // Get roles of the authenticated user
            var authorities = authentication.getAuthorities();
            boolean isAdmin = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            boolean isUser = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));
    
            // Redirect logic
            if (continueUrl != null && !continueUrl.isEmpty()) {
                response.sendRedirect(continueUrl); // Redirect to the continue URL if provided
            } else if (isAdmin) {
                response.sendRedirect("/admin/dashboard"); // Redirect to Admin dashboard
            } else if (isUser) {
                response.sendRedirect("/dashboard"); // Redirect to User dashboard
            } else {
                response.sendRedirect("/login?error"); // Default fallback
            }
        };
    }
    

    
}
