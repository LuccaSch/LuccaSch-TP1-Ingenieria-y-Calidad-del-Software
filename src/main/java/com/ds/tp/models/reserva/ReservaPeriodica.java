package com.ds.tp.models.reserva;

import java.sql.Timestamp;

import com.ds.tp.models.usuario.Bedel;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva_periodica")
public class ReservaPeriodica extends Reserva{
    /*
    atributos heredados:

    int id;
    int idDocente;
    int idAsignatura;
    String nombreDocente;
    String nombreAsignatura;
    int cantAlumnos;
    Timestamp fechaRegistro;
    Bedel bedel;
    */

    //atributos
    @ManyToOne  
    @JoinColumn(name = "id_periodo",referencedColumnName="id", nullable = false)
    private Periodo periodo;
    
    //constructores

    //constructor: por defecto
    public ReservaPeriodica(){}

    //constructor: todos los campos
    public ReservaPeriodica(Bedel bedel, Integer cantAlumnos, Timestamp fechaRegistroTimestamp, Long idAsignatura, Long idDocente, String nombreAsignatura, String nombreDocente,Periodo periodo) {
        this.bedel = bedel;
        this.cantAlumnos = cantAlumnos;
        this.fechaRegistroTimestamp = fechaRegistroTimestamp;
        this.idAsignatura = idAsignatura;
        this.idDocente = idDocente;
        this.nombreAsignatura = nombreAsignatura;
        this.nombreDocente = nombreDocente;
        this.periodo=periodo;
    }

    //constructor: Solo los campos que no pueden ser null
    public ReservaPeriodica(Bedel bedel, Integer cantAlumnos, Timestamp fechaRegistroTimestamp, Long idAsignatura, Long idDocente,Periodo periodo){
        this.bedel = bedel;
        this.cantAlumnos = cantAlumnos;
        this.fechaRegistroTimestamp = fechaRegistroTimestamp;
        this.idAsignatura = idAsignatura;
        this.idDocente = idDocente;
        this.periodo=periodo;
    }

    //getter/setter
    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }
}
