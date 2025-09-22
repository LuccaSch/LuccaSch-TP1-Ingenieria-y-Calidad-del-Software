package com.ds.tp.models.reserva;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ds.tp.models.aula.Aula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="dia_reserva")
public class DiaReserva{
    //atributos
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="fecha_reserva",nullable=false)
    private LocalDate fechaReserva;

    @Column(name="hora_inicio",nullable=false)
    private LocalTime horaInicio;

    @Column(name="duracion",nullable=false)
    private int duracion;
    
    @ManyToOne
    @JoinColumn(name = "id_aula",referencedColumnName="id",nullable=false)
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "id_reserva",referencedColumnName="id",nullable=false)
    private Reserva reserva;


    //constructores

    //constructor: por defecto
    public DiaReserva(){}

    //constructor: todos los campos
    public DiaReserva(Aula aula, Integer duracion, LocalDate fechaReserva, LocalTime horaInicio) {
        this.aula = aula;
        this.duracion = duracion;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
    }
    
    //getter/setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getFechaReserva() {
        return fechaReserva;
    }
    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public Aula getAula() {
        return aula;
    }
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
