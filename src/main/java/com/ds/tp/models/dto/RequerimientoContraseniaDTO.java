package com.ds.tp.models.dto;



public class RequerimientoContraseniaDTO{

    //a futuro habria que hacer el mapeo a json de los resultados de la consulta GET

    private Integer cantDigitos;
    private Integer cantMayusculas;
    private Integer cantNumeros;

    //constructores
    public RequerimientoContraseniaDTO(){};

    public RequerimientoContraseniaDTO(Integer cantDigitos, Integer cantMayusculas, Integer cantNumeros) {
        this.cantDigitos = cantDigitos;
        this.cantMayusculas = cantMayusculas;
        this.cantNumeros = cantNumeros;
    }

    //getter / setter
    
    public Integer getCantDigitos() {
        return cantDigitos;
    }
    public void setCantDigitos(Integer cantDigitos) {
        this.cantDigitos = cantDigitos;
    }
    public Integer getCantMayusculas() {
        return cantMayusculas;
    }
    public void setCantMayusculas(Integer cantMayusculas) {
        this.cantMayusculas = cantMayusculas;
    }
    public Integer getCantNumeros() {
        return cantNumeros;
    }
    public void setCantNumeros(Integer cantNumeros) {
        this.cantNumeros = cantNumeros;
    }
}
