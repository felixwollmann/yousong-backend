package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // http.csrf().disable()
        // .authorizeHttpRequests(auth -> auth
        // .requestMatchers("/login", "/logout").permitAll()
        // // .requestMatchers(HttpMethod.POST, "/songs/**").authenticated()
        // .anyRequest().permitAll())
        // .formLogin()
        // .and()
        // .logout().logoutUrl("/logout")
        // .logoutSuccessHandler((req, res, auth) ->
        // res.setStatus(HttpServletResponse.SC_OK))
        // .and()
        // .userDetailsService(userDetailsService);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.setAllowedOrigins(List.of("http://localhost:5173")); // Allow frontend
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    return config;
                }))                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(
                        configurer -> configurer
                                // .requestMatchers("/login").permitAll()
                                .anyRequest().permitAll())
                .formLogin(login -> login
                        .successHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
                            response.getWriter().write("{\"message\": \"Login successful\"}");
                            response.getWriter().flush();
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                            response.getWriter().write("{\"error\": \"Invalid credentials\"}");
                            response.getWriter().flush();
                        }))

                .logout(logout -> logout
                        // .logoutUrl("/logout")
                        // .logoutSuccessUrl("/login?logout")
                        .permitAll());
        // .authenticationProvider(authenticationProvider())
        // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
