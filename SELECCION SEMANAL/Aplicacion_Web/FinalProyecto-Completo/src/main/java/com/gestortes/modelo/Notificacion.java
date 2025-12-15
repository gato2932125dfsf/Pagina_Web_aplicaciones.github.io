package com.gestortes.modelo;

import java.sql.Timestamp;

public class Notificacion {
    private int id;
    private String mensaje;
    private String tipo; // 'info', 'alerta', 'sistema'
    private boolean leido;
    private Timestamp fecha;
    private Integer usuarioId; // Nuevo: ID del destinatario (puede ser null)

    public Notificacion() {}

    public Notificacion(String mensaje, String tipo, Integer usuarioId) {
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.usuarioId = usuarioId;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public boolean isLeido() { return leido; }
    public void setLeido(boolean leido) { this.leido = leido; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
}