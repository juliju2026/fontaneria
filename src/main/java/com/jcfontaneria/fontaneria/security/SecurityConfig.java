package com.jcfontaneria.fontaneria.security;

import com.jcfontaneria.fontaneria.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final AuditoriaLogin auditoriaLogin;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          AuditoriaLogin auditoriaLogin) {
        this.userDetailsService = userDetailsService;
        this.auditoriaLogin = auditoriaLogin;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(
                userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider(passwordEncoder()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/usuarios/**").hasRole("ADMINISTRADOR")
                        .requestMatchers("/clientes/**").hasAnyRole(
                                "ADMINISTRADOR", "COMERCIAL", "CONSULTA")
                        .requestMatchers("/averias/**").hasAnyRole(
                                "ADMINISTRADOR", "COMERCIAL", "CONSULTA")
                        .requestMatchers("/categorias/**").hasAnyRole(
                                "ADMINISTRADOR", "ALMACEN", "CONSULTA")
                        .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(auditoriaLogin)
                        .failureHandler(auditoriaLogin)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/acceso-denegado")
                );

        // CSRF permanece habilitado (configuración predeterminada).
        return http.build();
    }
}
