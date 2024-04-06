package com.study.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
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
                    authz.anyRequest().authenticated();
                })

                .formLogin(login -> login.permitAll())
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
