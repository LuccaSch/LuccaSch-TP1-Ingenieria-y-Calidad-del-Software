package com.ds.tp.models.reserva;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="periodo")
public class Periodo {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="cuatrimestre")
    private String cuatrimestre; //crear el enum ¿?

    @Column(name="fecha_inicio",nullable=false)
    private LocalDate fechaInicio;

    @Column(name="fecha_fin",nullable=false)
    private LocalDate fechaFin;

    //constructores

    //constructor: por defecto
    public Periodo(){}

    //constructor: todos los campos
    public Periodo(String cuatrimestre,LocalDate fechaInicio, LocalDate fechaFin) {
        this.cuatrimestre = cuatrimestre;
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
    }

    //constructor: Solo los campos que no pueden ser null
    public Periodo(LocalDate fechaInicio,LocalDate fechaFin){
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
    }

    //getter/setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCuatrimestre() {
        return cuatrimestre;
    }
    public void setCuatrimestre(String cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
