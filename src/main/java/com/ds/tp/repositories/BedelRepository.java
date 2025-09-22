package com.ds.tp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ds.tp.models.usuario.Bedel;


@Repository
public interface BedelRepository extends JpaRepository<Bedel,Long> {
    
   public Optional<Bedel> findByUsuario(String usuario);
}
