package taller2.tramaback.Config;

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

    // El CorsFilter bean y la configuración cors() en HttpSecurity pueden ser redundantes.
    // Usualmente se usa uno o el otro. Aquí mantengo ambos como en tu original,
    // pero la configuración en HttpSecurity suele tener precedencia o ser más específica.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Estos orígenes deben coincidir con los de la configuración de HttpSecurity
        config.addAllowedOrigin("http://localhost:3000");
        //config.addAllowedOrigin("https://trama-gamma.vercel.app");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> { // Esta configuración de CORS es la que Spring Security usa directamente.
                CorsConfiguration configuration = new CorsConfiguration();
                // Usar addAllowedOriginPattern para mayor flexibilidad si los puertos cambian o hay subdominios.
                configuration.addAllowedOriginPattern("http://localhost:3000");
                //configuration.addAllowedOriginPattern("https://trama-gamma.vercel.app");
                // Si usas un puerto diferente para React en ReviewController (3001), añádelo también:
                // configuration.addAllowedOriginPattern("http://localhost:3001");

                configuration.addAllowedMethod("*"); // Permite todos los métodos (GET, POST, PUT, DELETE, etc.)
                configuration.addAllowedHeader("*"); // Permite todas las cabeceras
                configuration.setAllowCredentials(true); // Importante para cookies, autenticación basada en sesión, etc.
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
                    .requestMatchers(HttpMethod.GET, "/trama/lists/**").permitAll() // Ver listas públicas (cubre /user/{userId} y /{id})
                    .requestMatchers(HttpMethod.GET, "/trama/review-likes/**").permitAll() // NUEVO: Ver likes de reviews y reviews que gustaron
                    .requestMatchers(HttpMethod.GET, "/trama/list-likes/**").permitAll() // Permitir GET para list-likes también
                    // Cualquier otra petición requiere autenticación
                    .anyRequest().authenticated()
            );

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}