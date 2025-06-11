package taller2.tramaback.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Importar HttpMethod
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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import taller2.tramaback.Security.Jwt.AuthEntryPointJwt;
import taller2.tramaback.Security.Jwt.AuthTokenFilter;
import taller2.tramaback.Security.Services.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000"); // Or your React app's origin
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.addAllowedOriginPattern("http://localhost:3000");
                configuration.addAllowedOriginPattern("https://miapp.com");
                configuration.addAllowedMethod("*");
                configuration.addAllowedHeader("*");
                configuration.setAllowCredentials(true);
                return configuration;
            }))
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth ->
                auth.requestMatchers("/trama/auth/**").permitAll() // Login
                    .requestMatchers(HttpMethod.POST, "/trama/users/save").permitAll() // Registro de usuarios
                    // Permitir GET para visualización de contenido público
                    .requestMatchers(HttpMethod.GET, "/trama/reviews/**").permitAll() // Ver reviews
                    .requestMatchers(HttpMethod.GET, "/trama/comments/**").permitAll() // Ver comentarios
                    .requestMatchers(HttpMethod.GET, "/movies/**").permitAll() // Ver información de películas
                    .requestMatchers(HttpMethod.GET, "/trama/users/id/**").permitAll() // Ver perfil público de usuario por ID
                    .requestMatchers(HttpMethod.GET, "/trama/users/email/**").permitAll() // Ver perfil público por email
                    .requestMatchers(HttpMethod.GET, "/trama/users/name/**").permitAll() // Ver perfil público por nombre
                    .requestMatchers(HttpMethod.GET, "/trama/lists/**").permitAll() // Ver listas públicas
                    // Cualquier otra petición requiere autenticación
                    .anyRequest().authenticated()
            );

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}