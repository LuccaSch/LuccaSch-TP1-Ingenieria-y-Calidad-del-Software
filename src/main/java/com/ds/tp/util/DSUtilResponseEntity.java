package com.ds.tp.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DSUtilResponseEntity {

    //GENERICOS

    //exito generico

    public static ResponseEntity<Object> success(String mensaje, Object data) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("estado", true);
        respuesta.put("data", data);
        return ResponseEntity.ok(respuesta);
    }

    //exito generico con datos.
    
    public static ResponseEntity<Object> successConDatos(String mensaje, Object data) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("estado", true);
        respuesta.put("data", data);
        return ResponseEntity.ok(respuesta);
    }

    //error generico

    public static ResponseEntity<Object> error(String mensaje, HttpStatus status) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("estado", false);
        return ResponseEntity.status(status).body(respuesta);
    }

    //error generico pero se agregan datos

    public static ResponseEntity<Object> errorConDatos(String mensaje, HttpStatus status, Object data) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("estado", false);
        respuesta.put("data", data);
        return ResponseEntity.status(status).body(respuesta);
    }

    //Especificos

    //Bad request
    public static ResponseEntity<Object> statusBadRequest(String mensaje) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("estado", false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    //Conflict
    public static ResponseEntity<Object> statusConflict(String mensaje) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("estado", false);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
    }

    //Internal Server Error
    public static ResponseEntity<Object> statusInternalServerError(String mensaje) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("estado", false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }

    //Created
    public static ResponseEntity<Object> statusCreated(String mensaje) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("estado", true);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    //OK
    public static ResponseEntity<Object> statusOk(String mensaje,Object resultado) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        respuesta.put("resultado", resultado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    //OK
    public static ResponseEntity<Object> statusOk(Object resultado) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("resultado", resultado);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    public static ResponseEntity<Object> statusOk(String mensaje){
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    //UNAUTHORIZED
    public static ResponseEntity<Object> statusUnauthorized(String mensaje){
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
    }

}

