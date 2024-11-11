package com.group.an.authService.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter(jwtTokenUtil, userDetailsService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
        customAuthenticationProvider.setUserDetailsService(userDetailsService);
        customAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return customAuthenticationProvider;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Disable CSRF - Cross Site Request Forgery - As we are stateless backend
        http.csrf(AbstractHttpConfigurer::disable)
            //Set Authorization Policies
            .authorizeHttpRequests(
                                //Permit all requests to Auth & Swagger Endpoints
                    auth -> auth.requestMatchers("/auth/register/**","/auth/login/**","/error/**").permitAll()
                                .requestMatchers("/v3/api-docs/**","/swagger-resources/**","/swagger-resources","/swagger-ui/**").permitAll()
                                //Require authentication for all other endpoints
                                .anyRequest().authenticated()
            )
            //Set Exception Handling Policy
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint((request, response, authException) -> {
                        LOGGER.error("Unauthorized error: {}", authException.getMessage());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        final Map<String, Object> body = new HashMap<>();
                        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
                        body.put("error", "Unauthorized");
                        body.put("message", authException.getMessage());
                        body.put("path", request.getServletPath());
                        final ObjectMapper mapper = new ObjectMapper();
                        mapper.writeValue(response.getOutputStream(), body);
                    })
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        LOGGER.error("Unauthorized error: {}", accessDeniedException.getMessage());
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        final Map<String, Object> body = new HashMap<>();
                        body.put("status", HttpServletResponse.SC_FORBIDDEN);
                        body.put("error", "Forbidden");
                        body.put("message", accessDeniedException.getMessage() + ", you do not have the necessary role to access this resource.");
                        body.put("path", request.getServletPath());
                        final ObjectMapper mapper = new ObjectMapper();
                        mapper.writeValue(response.getOutputStream(), body);
                    })
            )
            //Set as Stateless session as we are not storing any user session info
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //Set service which will provide info about usernames & their roles
        http.authenticationProvider(authenticationProvider());

        //Add JWT Filter for all requests
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
