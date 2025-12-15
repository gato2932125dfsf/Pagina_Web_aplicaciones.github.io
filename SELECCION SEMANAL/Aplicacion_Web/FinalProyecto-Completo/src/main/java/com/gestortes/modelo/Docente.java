package com.gestortes.modelo;

/**
 * JavaBean (POJO) que representa a un Docente de la tabla 'docentes'.
 */
public class Docente {
    
    private int id;
    private String nombre;
    private String email;
    private String dni;
    private String password; // Añadido para el formulario de creación

    public Docente() {
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}