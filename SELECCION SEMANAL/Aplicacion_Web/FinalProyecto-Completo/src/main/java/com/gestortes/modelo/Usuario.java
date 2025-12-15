package com.gestortes.modelo;

/**
 * JavaBean (POJO) que representa a un usuario
 * logueado desde la vista "vista_login_usuarios".
 */
public class Usuario {
    
    private int id;
    private String nombre;
    private String email;
    private String rol;
    private String codigoUsuario; // DNI (docente) o Código (alumno) o Email (admin)

    // Constructor vacío
    public Usuario() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }
}