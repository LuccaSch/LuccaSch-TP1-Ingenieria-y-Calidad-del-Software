package com.ds.tp.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ds.tp.models.usuario.Administrador;

@Repository
public interface AdminRepository extends JpaRepository<Administrador,Long>{

   public Optional<Administrador> findByUsuario(String usuario);

}
