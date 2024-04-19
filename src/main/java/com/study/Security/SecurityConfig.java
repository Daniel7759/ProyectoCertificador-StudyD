package com.study.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> {
                    authz.requestMatchers("/users/**").permitAll();
                    authz.requestMatchers("/levels/**").permitAll();
                    authz.requestMatchers("/cursos/**").permitAll();
                    authz.requestMatchers("/materias/**").permitAll();
                    authz.requestMatchers("/temas/**").permitAll();
                    authz.requestMatchers("/subtemas/**").permitAll();
                    authz.requestMatchers("/tareas/**").permitAll();
                    authz.requestMatchers("/usercursos/**").permitAll();
                    authz.requestMatchers("/usercursos/userdto/2").permitAll();
                    authz.requestMatchers("/logros/**").permitAll();
                    authz.requestMatchers("/preguntas/**").permitAll();
                    authz.requestMatchers("/examenes/**").permitAll();
                    authz.requestMatchers("/tokens/**").permitAll();
                    authz.anyRequest().authenticated();
                })

                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .httpBasic(Customizer.withDefaults())
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings("removal")
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
            throws Exception{
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder).and()
                .build();
    }
}
