// Archivo: WebSecurityConfig.java (Versión Migrada a Spring Boot 3.3.0)
package co.com.juan.poly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // <-- Cambio de anotación
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect; // <-- ¡Nuevo Import!

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // <-- Reemplaza @EnableGlobalMethodSecurity
public class WebSecurityConfig {

    // Nota: customUserDetailsService ahora se inyecta automáticamente si es @Service,
    // pero lo dejamos como @Autowired por si se necesita en otra parte.
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // 1. Configuración del Codificador de Contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() { // <-- Usamos la interfaz PasswordEncoder
        return new BCryptPasswordEncoder();
    }

    // 2. Dialecto de Thymeleaf (Nuevo paquete)
    @Bean
    public SpringSecurityDialect securityDialect() {
        // Usar la clase del nuevo paquete
        return new SpringSecurityDialect();
    }

    // 3. El Nuevo SecurityFilterChain (Reemplaza configure(HttpSecurity))
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Deshabilitar CSRF (Crucial para H2)
                .csrf(csrf -> csrf.disable()) // Opcional, pero H2 lo necesita.

                // 2. Definir las reglas de autorización
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acceso a la consola H2
                        .requestMatchers("/h2-console/**").permitAll()
                        // Permite acceso a archivos estáticos, login, etc.
                        .requestMatchers("/css/**", "/js/**", "/login", "/logout").permitAll()
                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )
                // 3. Configuración de Login
                .formLogin(form -> form
                        .loginPage("/login") // Asegura que esta es tu página de login
                        .permitAll()
                )
                // 4. Configuración de Logout
                .logout(logout -> logout
                        .permitAll()
                );

        // **IMPORTANTE para H2:** Permitir que los frames funcionen
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }


    // NOTA: Ya NO necesitas el metodo 'configAuthentication' (o 'configureGlobal').
    // Spring Boot 3.3 detecta automáticamente tu CustomUserDetailsService (@Service) y tu PasswordEncoder (@Bean).
}