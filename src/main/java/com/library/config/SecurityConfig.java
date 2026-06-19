package com.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Public resources
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/login"
                        ).permitAll()

                        // Student pages
                        .requestMatchers("/student/**")
                        .hasRole("STUDENT")

                        // Admin & Librarian pages
                        .requestMatchers(
                                "/dashboard",
                                "/books/**",
                                "/students/**",
                                "/issues/**",
                                "/book-issues/**",
                                "/fines/**",
                                "/reports/**"
                        ).hasAnyRole("ADMIN", "LIBRARIAN")

                        // Admin only
                        .requestMatchers("/settings/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")

                        // Role-based redirection
                        .successHandler((request, response, authentication) -> {

                            boolean isStudent = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(auth ->
                                            auth.getAuthority().equals("ROLE_STUDENT"));

                            if (isStudent) {
                                response.sendRedirect("/student/dashboard");
                            } else {
                                response.sendRedirect("/dashboard");
                            }
                        })

                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        UserDetails librarian = User.withUsername("librarian")
                .password("{noop}lib123")
                .roles("LIBRARIAN")
                .build();

        UserDetails student = User.withUsername("spoorthireddyy26@gmail.com")
                .password("{noop}student123")
                .roles("STUDENT")
                .build();
        
        UserDetails student1 = User.withUsername("rgowtham919@gmail.com")
                .password("{noop}student123")
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(
                admin,
                librarian,
                student,
                student1
        
        );
    }
}