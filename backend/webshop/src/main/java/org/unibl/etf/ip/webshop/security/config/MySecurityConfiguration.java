package org.unibl.etf.ip.webshop.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.services.UserService;
import org.unibl.etf.ip.webshop.security.JwtAuthorizationFilter;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.ORIGIN;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
@Configuration
@EnableWebSecurity

public class MySecurityConfiguration {



    private final UserService userService;

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    private final PasswordEncoder encoder;

    public MySecurityConfiguration(UserService userService, JwtAuthorizationFilter jwtAuthorizationFilter, PasswordEncoder encoder) {
        this.userService = userService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;

        this.encoder = encoder;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                    .and()
                .csrf()
                    .disable()
                .authorizeHttpRequests()
                    .requestMatchers("/api/v1/auth/**",
                            "/api/v1/products/{id}/images",
                            "/api/v1/products/search",
                            "/api/v1/products/{id}",
                            "/api/v1/products",
                            "/api/v1/categories/product/{id}",
                            "/api/v1/comments/{id}",
                            "/api/v1/users/avatar/{username}",
                            "/api/v1/images/{id}/one",
                            "/api/v1/categories/{id}/attributes",
                            "/api/v1/attributes/product/{id}",
                            "/api/v1/categories",
                            "/api/v1/categories/{id}",
                            "/api/v1/logs/**"
                           )
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()


                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/auth/signup");
//    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

       //config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));


        List<String> allowedOrigins = Arrays.asList(
                "http://localhost:4200",


                "http://localhost:8080"


        );
        config.setAllowedOrigins(allowedOrigins);


        config.setAllowedHeaders(Arrays.asList(
                ORIGIN,
                CONTENT_TYPE,
                ACCEPT,
                AUTHORIZATION,
                ACCESS_CONTROL_ALLOW_CREDENTIALS,
                ACCESS_CONTROL_ALLOW_ORIGIN,
                ACCESS_CONTROL_ALLOW_HEADERS
        ));
        config.setAllowedMethods(Arrays.asList(
                GET.name(),
                POST.name(),
                DELETE.name(),
                PUT.name(),
                PATCH.name()
        ));
        config.addExposedHeader("Errormessage");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);

    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(username -> {
            try {
                return userService.findByUsername(username);
            } catch (NotFoundException e) {
                return null;
            }
        });
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }





}
