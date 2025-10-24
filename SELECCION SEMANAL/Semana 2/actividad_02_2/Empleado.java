/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_2;

/**
 *
 * @author Pavilion
 */
public class Empleado {
      private String nombre;
    private char categoria; // 'A' o 'B'
    private double horasTrabajadas;
    private int numeroHijos;
    
    // Constructor
    public Empleado(String nombre, char categoria, double horasTrabajadas, int numeroHijos) {
        this.nombre = nombre;
        this.categoria = Character.toUpperCase(categoria);
        this.horasTrabajadas = horasTrabajadas;
        this.numeroHijos = numeroHijos;
    }
    
    public Empleado(char categoria, double horasTrabajadas, int numeroHijos) {
        this.nombre = "Empleado";
        this.categoria = Character.toUpperCase(categoria);
        this.horasTrabajadas = horasTrabajadas;
        this.numeroHijos = numeroHijos;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public char getCategoria() {
        return categoria;
    }
    
    public void setCategoria(char categoria) {
        this.categoria = Character.toUpperCase(categoria);
    }
    
    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }
    
    public void setHorasTrabajadas(double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }
    
    public int getNumeroHijos() {
        return numeroHijos;
    }
    
    public void setNumeroHijos(int numeroHijos) {
        this.numeroHijos = numeroHijos;
    }
    
}
