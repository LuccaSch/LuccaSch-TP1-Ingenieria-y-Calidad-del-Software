package com.ds.tp.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ds.tp.services.securityconfig.DSUserDetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Config {
    @Autowired
    private final DSUserDetailsService userDetailsService;

    @Autowired
    private final DSAuthenticationSuccessHandler successHandler;

    //private final PasswordEncoder passwordEncoder;

    String[] publicResources = new String[]{"/","/css/index.css","/css/login.css","img/UTN_logo_public.png"};
    String[] bedelResources = new String[]{"/bedel/**"};
    String[] adminResources = new String[]{"/admin/**"};

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usamos BCrypt para una encriptación mas robusta que m5
        return new BCryptPasswordEncoder(); 
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return auth.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(publicResources).permitAll()
            .requestMatchers("/current/api/user").authenticated()
            .requestMatchers(bedelResources).hasAuthority("ROLE_BEDEL")
            .requestMatchers(adminResources).hasAuthority("ROLE_ADMIN")  
            .anyRequest().authenticated()
            )
        .formLogin(form -> form
                        .successHandler(successHandler)
                        .loginPage("/login")
                        .permitAll()
                    )
        .logout(logout -> logout.permitAll())
        .build();
    }
}
