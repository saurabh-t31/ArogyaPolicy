package com.g1springboard.ArogyaPolicy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                        .requestMatchers("/user/register", "/user/login").permitAll() // Allow register & login endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .httpBasic(Customizer.withDefaults())
                // .formLogin(form -> form
                //         .loginPage("/login") // Custom login page (if applicable)
                //         .loginProcessingUrl("/login") // URL where login POST requests are processed
                //         .permitAll()
                // )
                // .logout(logout -> logout
                //         .invalidateHttpSession(true) // Clear session on logout
                //         .clearAuthentication(true) // Clear authentication
                //         .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Logout URL
                //         .permitAll()
                // )
                .build(); // Return the configured SecurityFilterChain
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
}
