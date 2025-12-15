package com.gestortes.modelo;

import java.sql.Timestamp;

public class HistorialRevision {
    
    private int id;
    private int tesisId;
    private int numeroVersion;
    private String archivoUrl;
    private String comentariosDocente;
    private String estadoVersion;
    private Timestamp fechaSubida;
    
    // NUEVOS CAMPOS PARA RÚBRICA
    private double puntajeTotal;
    private String detalleRubrica; // "1.0,0.5,..."

    public HistorialRevision() {
    }

    // Getters y Setters existentes...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTesisId() { return tesisId; }
    public void setTesisId(int tesisId) { this.tesisId = tesisId; }
    public int getNumeroVersion() { return numeroVersion; }
    public void setNumeroVersion(int numeroVersion) { this.numeroVersion = numeroVersion; }
    public String getArchivoUrl() { return archivoUrl; }
    public void setArchivoUrl(String archivoUrl) { this.archivoUrl = archivoUrl; }
    public String getComentariosDocente() { return comentariosDocente; }
    public void setComentariosDocente(String comentariosDocente) { this.comentariosDocente = comentariosDocente; }
    public String getEstadoVersion() { return estadoVersion; }
    public void setEstadoVersion(String estadoVersion) { this.estadoVersion = estadoVersion; }
    public Timestamp getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(Timestamp fechaSubida) { this.fechaSubida = fechaSubida; }

    // Nuevos Getters y Setters
    public double getPuntajeTotal() { return puntajeTotal; }
    public void setPuntajeTotal(double puntajeTotal) { this.puntajeTotal = puntajeTotal; }
    public String getDetalleRubrica() { return detalleRubrica; }
    public void setDetalleRubrica(String detalleRubrica) { this.detalleRubrica = detalleRubrica; }
}