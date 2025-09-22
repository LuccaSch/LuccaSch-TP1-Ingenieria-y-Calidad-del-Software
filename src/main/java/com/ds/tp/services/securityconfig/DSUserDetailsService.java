package com.ds.tp.services.securityconfig;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ds.tp.models.usuario.Administrador;
import com.ds.tp.models.usuario.Bedel;
import com.ds.tp.repositories.AdminRepository;
import com.ds.tp.repositories.BedelRepository;

@Service
public class DSUserDetailsService implements UserDetailsService {

    @Autowired
    private BedelRepository bedelRepository; 
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        // Primero buscamos si el usuario está registrado como bedel
        
        Optional<Bedel> bedel = bedelRepository.findByUsuario(user);

        if (bedel.isPresent() && bedel.get().isEstado()) {
            return new org.springframework.security.core.userdetails.User(
                    bedel.get().getUsuario(),
                    bedel.get().getContrasenia(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_BEDEL"))
            );
        }

        // Si no es bedel, buscamos si es administrador

        Optional<Administrador> admin = adminRepository.findByUsuario(user);

        if (admin.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    admin.get().getUsuario(),
                    admin.get().getContrasenia(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}
