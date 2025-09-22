package com.ds.tp.services;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ds.tp.exception.ReservaSuperpuestaException;
import com.ds.tp.models.aula.Aula;
import com.ds.tp.models.dto.DiaReservaDTO;
import com.ds.tp.models.dto.ReservaDTO;
import com.ds.tp.models.reserva.DiaReserva;
import com.ds.tp.models.reserva.Periodo;
import com.ds.tp.models.reserva.Reserva;
import com.ds.tp.models.reserva.ReservaEsporadica;
import com.ds.tp.models.reserva.ReservaPeriodica;
import com.ds.tp.models.usuario.Bedel;
import com.ds.tp.repositories.AulaRepository;
import com.ds.tp.repositories.BedelRepository;
import com.ds.tp.repositories.ReservaRepository;
import com.ds.tp.util.DSUtilResponseEntity;

@Service
public class ReservaService {
    //ATRIBUTOS

    // Atributos inyectados por Spring
    @Autowired
    private final BedelRepository bedelRepository;

    @Autowired
    private final AulaRepository aulaRepository;

    @Autowired
    private final ReservaRepository reservaRepository;

    //Atributos propios de la clase
    private static final int PERIODICA=0;
    private static final int ESPORADICA=1; 

    //Constructor

    public ReservaService(BedelRepository bedelRepository, AulaRepository aulaRepository,ReservaRepository reservaRepository) {
        this.bedelRepository = bedelRepository;
        this.aulaRepository=aulaRepository;
        this.reservaRepository=reservaRepository;
    }

    // FUNCIONES DEL SERVICIO RESERVA

    //--------------------------------------------------POST RESERVA--------------------------------------------------   

    public ResponseEntity<Object> postReserva(ReservaDTO reservaDTO){
        try {
            if(verificarDatosIncorrectosReserva(reservaDTO)){
                return DSUtilResponseEntity.statusBadRequest("ERROR: No se puede crear la reserva porque existen datos invalidos dentro de la solicitud");
            }
    
            switch (reservaDTO.getTipo()) {
                case PERIODICA -> {
                    this.convertirPeriodica(reservaDTO);
                    return crearReservaPeriodica(reservaDTO);
                }
                case ESPORADICA -> {
                    return crearReservaEsporadica(reservaDTO);
                }
                default -> {
                    return DSUtilResponseEntity.statusBadRequest("ERROR: Se quiere crear una reserva de un tipo invalido");
                }
            }
        }         
        catch (DataAccessException e) {
            return DSUtilResponseEntity.statusInternalServerError("Error interno del Servidor, por favor intentar mas tarde");
        }
        catch(IllegalStateException e){
            return DSUtilResponseEntity.statusInternalServerError(e.getMessage());
        }
        catch (ReservaSuperpuestaException e) {
            return DSUtilResponseEntity.statusBadRequest(e.getMessage());
        } 
        catch (Exception e) {
            return DSUtilResponseEntity.statusInternalServerError("Error inesperado, por favor intentar mas tarde, si el error continua contactarse con soporte");
        }
    }

    public void convertirPeriodica(ReservaDTO reservaDTO) throws IllegalStateException{

        Optional<Periodo> periodoOpt = reservaRepository.findPeriodoById(reservaDTO.getPeriodo());

        if(periodoOpt.isEmpty()){
            throw new IllegalStateException("ERROR: Se quiere asignar un periodo invalido a la reserva");
        }

        Periodo periodo = periodoOpt.get();
        
        List<DiaReservaDTO> diasReservaConvertidos = new ArrayList<>();

        Period semana = Period.ofDays(7);

        for(DiaReservaDTO diaReservaDTO : reservaDTO.getListaDiasReservaDTO()){
            LocalDate fechaAux = periodo.getFechaInicio().with(TemporalAdjusters.next(diaReservaDTO.getDiaSemana()));
            while(fechaAux.isBefore(periodo.getFechaFin()) || fechaAux.equals(periodo.getFechaFin())){
                diasReservaConvertidos.add(new DiaReservaDTO(diaReservaDTO.getDuracion(), fechaAux, diaReservaDTO.getHoraInicio(),diaReservaDTO.getIdAula()));
                fechaAux=fechaAux.plus(semana);
            }
        }

        reservaDTO.setListaDiasReservaDTO(diasReservaConvertidos);
    }

    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<Object> crearReservaEsporadica(ReservaDTO reservaDTO) {
        try{
            ReservaEsporadica nuevaReservaEsporadica = new ReservaEsporadica(this.obtenerUsuarioLogeado(),
                                                                reservaDTO.getCantAlumnos(),
                                                                Timestamp.from(Instant.now()),
                                                                reservaDTO.getIdAsignatura(), 
                                                                reservaDTO.getIdDocente(),
                                                                reservaDTO.getNombreAsignatura(),
                                                                reservaDTO.getNombreDocente()
                                                            );
            

            nuevaReservaEsporadica.setDiasReserva(this.crearDiasReserva(
                                                reservaDTO.getListaDiasReservaDTO())
                                                );

            for (DiaReserva diaReserva : nuevaReservaEsporadica.getDiasReserva()) {
                diaReserva.setReserva(nuevaReservaEsporadica);
            }

            this.saveReserva(nuevaReservaEsporadica);

            return DSUtilResponseEntity.statusOk("Se guardo la Reserva con exito");

        }
        catch (DataAccessException e) {
            return DSUtilResponseEntity.statusInternalServerError("Error interno del Servidor, por favor intentar mas tarde");
        }
        catch(IllegalStateException | ReservaSuperpuestaException e){
            return DSUtilResponseEntity.statusInternalServerError(e.getMessage());
        }
        catch (Exception e) {
            return DSUtilResponseEntity.statusInternalServerError("Error inesperado, por favor intentar mas tarde, si el error continua contactarse con soporte");
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<Object> crearReservaPeriodica(ReservaDTO reservaDTO) {
        try{
            Optional<Periodo> periodoOptional = reservaRepository.findPeriodoById(reservaDTO.getPeriodo());
            
            if(periodoOptional.isEmpty()){
                DSUtilResponseEntity.statusInternalServerError("Se quiere asignar un periodo invalido a la reserva");
            }

            ReservaPeriodica nuevaReservaPeriodica = new ReservaPeriodica(this.obtenerUsuarioLogeado(),
                                                        reservaDTO.getCantAlumnos(),
                                                        Timestamp.from(Instant.now()),
                                                        reservaDTO.getIdAsignatura(),
                                                        reservaDTO.getIdDocente(),
                                                        reservaDTO.getNombreAsignatura(),
                                                        reservaDTO.getNombreDocente(),
                                                        periodoOptional.get()
                                                    );

            nuevaReservaPeriodica.setDiasReserva(this.crearDiasReserva(reservaDTO.getListaDiasReservaDTO()));

            for (DiaReserva diaReserva : nuevaReservaPeriodica.getDiasReserva()) {
                diaReserva.setReserva(nuevaReservaPeriodica);
            }

            this.saveReserva(nuevaReservaPeriodica);

            return DSUtilResponseEntity.statusOk("Se guardo la Reserva con exito");

        }
        catch (DataAccessException e) {
            return DSUtilResponseEntity.statusInternalServerError("Error interno del Servidor, por favor intentar mas tarde:" +e.getMessage());
        }
        catch(IllegalStateException e){
            return DSUtilResponseEntity.statusInternalServerError(e.getMessage());
        }
        catch (Exception e) {
            return DSUtilResponseEntity.statusInternalServerError("Error inesperado, por favor intentar mas tarde, si el error continua contactarse con soporte");
        }
    }


    public synchronized void saveReserva(Reserva reserva) throws ReservaSuperpuestaException {
        List<DiaReserva> diasReserva = reserva.getDiasReserva();

        if (calcularSuperposicion(diasReserva)) {
            throw new ReservaSuperpuestaException("Lamentamos informarle que se ha producido una superposición de horarios, no pudimos registrar su reserva.");
        }
        else{
            reservaRepository.save(reserva);
        }
    }

    //--------------------------------------------------METODOS GENERALES--------------------------------------------------

    public List<DiaReserva> crearDiasReserva(List<DiaReservaDTO> listaDiasReservaDTO){
        List<DiaReserva> diasReserva = new ArrayList<>();

        for(DiaReservaDTO diaReservaDTO : listaDiasReservaDTO){
            diasReserva.add(crearDiaReserva(diaReservaDTO));
        }

        return diasReserva;
    }

    public DiaReserva crearDiaReserva(DiaReservaDTO diaReservaDTO){
        Optional<Aula> aulaOptional = aulaRepository.findById(diaReservaDTO.getIdAula());

        if(aulaOptional.isPresent()){
            return new DiaReserva(aulaOptional.get(), diaReservaDTO.getDuracion(), diaReservaDTO.getFechaReserva(), diaReservaDTO.getHoraInicio());
        }
        
        throw new IllegalStateException("ERROR: Se pide crear una reserva de un Aula inexistente");
    }

    public Bedel obtenerUsuarioLogeado() throws IllegalStateException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("ERROR: No hay un usuario autenticado.");
        }
        // Sabemos que siempre será UserDetails porque usamos UserDetailsService
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<Bedel> opcionalBedel =  bedelRepository.findByUsuario(userDetails.getUsername());

        if(opcionalBedel.isPresent()){
            return opcionalBedel.get();
        }

        throw new IllegalStateException("ERROR: No existe el usuario Solicitado");
    }

    public boolean calcularSuperposicion(List<DiaReserva> diasReserva) {
        for (DiaReserva diaReserva : diasReserva) {
            List<DiaReserva> diasExistentes = reservaRepository.findDiaReservaByFechaReservaAndAula(diaReserva.getFechaReserva(), diaReserva.getAula().getIdAula());
            for (DiaReserva diaExistente : diasExistentes) {
                if (haySuperposicion(diaReserva, diaExistente)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean haySuperposicion(DiaReserva diaReserva, DiaReserva diaExistente) {
        Duration duracion1 = Duration.ofMinutes(diaExistente.getDuracion());
        Duration duracion2 = Duration.ofMinutes(diaReserva.getDuracion());
    
        // Hora de inicio y fin de cada reserva
        LocalTime inicio1 = diaExistente.getHoraInicio();
        LocalTime inicio2 = diaReserva.getHoraInicio();
        LocalTime fin1 = inicio1.plus(duracion1);
        LocalTime fin2 = inicio2.plus(duracion2);
    
        // Calcular el mayor de los inicios y el menor de los finales
        LocalTime inicioSuperposicion = inicio1.isAfter(inicio2) ? inicio1 : inicio2;
        LocalTime finSuperposicion = fin1.isBefore(fin2) ? fin1 : fin2;
    
        // Verificar si hay superposición
        return inicioSuperposicion.isBefore(finSuperposicion);
    }



    public boolean verificarDatosIncorrectosReserva(ReservaDTO nuevaReserva){

        if(nuevaReserva.getIdDocente()==null || nuevaReserva.getIdAsignatura()==null || 
        nuevaReserva.getNombreDocente()==null || nuevaReserva.getNombreAsignatura()==null||
        nuevaReserva.getCantAlumnos()==null || nuevaReserva.getListaDiasReservaDTO()==null ){
            return true;           
        }

        if(nuevaReserva.getTipo()==PERIODICA){
            if (nuevaReserva.getPeriodo()==null){
                return true;  
            }
            if (!(nuevaReserva.getPeriodo().equals(0L) || nuevaReserva.getPeriodo().equals(1L) || nuevaReserva.getPeriodo().equals(2L))){
                return true;  
            }
        }

        return nuevaReserva.getListaDiasReservaDTO().isEmpty();
    }
}
