package com.ds.tp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ds.tp.models.aula.Aula;
import com.ds.tp.models.aula.AulaInformatica;
import com.ds.tp.models.aula.AulaMultimedia;
import com.ds.tp.models.aula.AulaSinRecursos;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    @Query("SELECT am FROM AulaMultimedia am WHERE am.maximoAlumnos >= :cantidadAlumnos AND am.aulaDisponible = true")
    Optional<List<AulaMultimedia>> findAulasMultimediaByMaximoAlumnos(@Param("cantidadAlumnos") Integer cantidadAlumnos);

    @Query("SELECT ai FROM AulaInformatica ai WHERE ai.maximoAlumnos >= :cantidadAlumnos AND ai.aulaDisponible = true")
    Optional<List<AulaInformatica>> findAulasInformaticaByMaximoAlumnos(@Param("cantidadAlumnos") Integer cantidadAlumnos);

    @Query("SELECT asr FROM AulaSinRecursos asr WHERE asr.maximoAlumnos >= :cantidadAlumnos AND asr.aulaDisponible = true")
    Optional<List<AulaSinRecursos>> findAulasSinRecursosByMaximoAlumnos(@Param("cantidadAlumnos") Integer cantidadAlumnos);

    @SuppressWarnings("null")
    @Override
    public Optional<Aula> findById(Long idAula);
}

