package com.ds.tp.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ds.tp.models.reserva.DiaReserva;
import com.ds.tp.models.reserva.Periodo;
import com.ds.tp.models.reserva.Reserva;



@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long>{

    @SuppressWarnings("null")
    @Override
    public Optional<Reserva> findById(Long id);

    @Query("SELECT p FROM Periodo p WHERE p.id = :periodoId")
    Optional<Periodo> findPeriodoById(@Param("periodoId") Long periodoId);

    @Query("SELECT ds FROM DiaReserva ds WHERE ds.fechaReserva = :fechaReserva")
    List<DiaReserva> findDiaReservaByFechaReserva(LocalDate fechaReserva);

    @Query("SELECT ds FROM DiaReserva ds WHERE ds.fechaReserva = :fechaReserva AND ds.aula.id = :idAula")
    List<DiaReserva> findDiaReservaByFechaReservaAndAula(@Param("fechaReserva") LocalDate fechaReserva, @Param("idAula") Long idAula);
}
