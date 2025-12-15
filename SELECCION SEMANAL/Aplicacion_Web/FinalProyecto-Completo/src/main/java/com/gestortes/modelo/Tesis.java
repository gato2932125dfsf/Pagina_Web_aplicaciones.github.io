package com.gestortes.modelo;

import java.sql.Timestamp;
import java.sql.Date; // Usamos sql.Date para fechas sin hora (plazos)

public class Tesis {
    
    private int id;
    private String titulo;
    private String descripcion;
    private String archivoActualUrl;
    private String estado;
    private Timestamp fechaUltimaModificacion;
    private Date fechaLimite; // NUEVO: Plazo límite asignado por docente
    
    private String nombreAlumno;
    private String nombreDocente;
    
    private int alumnoId;
    private int docenteId;

    public Tesis() {
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getArchivoActualUrl() { return archivoActualUrl; }
    public void setArchivoActualUrl(String archivoActualUrl) { this.archivoActualUrl = archivoActualUrl; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Timestamp getFechaUltimaModificacion() { return fechaUltimaModificacion; }
    public void setFechaUltimaModificacion(Timestamp fechaUltimaModificacion) { this.fechaUltimaModificacion = fechaUltimaModificacion; }

    public Date getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(Date fechaLimite) { this.fechaLimite = fechaLimite; }

    public String getNombreAlumno() { return nombreAlumno; }
    public void setNombreAlumno(String nombreAlumno) { this.nombreAlumno = nombreAlumno; }

    public String getNombreDocente() { return nombreDocente; }
    public void setNombreDocente(String nombreDocente) { this.nombreDocente = nombreDocente; }

    public int getAlumnoId() { return alumnoId; }
    public void setAlumnoId(int alumnoId) { this.alumnoId = alumnoId; }

    public int getDocenteId() { return docenteId; }
    public void setDocenteId(int docenteId) { this.docenteId = docenteId; }
}