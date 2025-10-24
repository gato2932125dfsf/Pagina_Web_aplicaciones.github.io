/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_1;

/**
 *
 * @author Pavilion
 */
public class Producto {
    private String nombre;
    private double precioDocena;
    
    // Constructor
    public Producto(String nombre, double precioDocena) {
        this.nombre = nombre;
        this.precioDocena = precioDocena;
    }
    
    public Producto(double precioDocena) {
        this.nombre = "Producto";
        this.precioDocena = precioDocena;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombsre) {
        this.nombre = nombre;
    }
    
    public double getPrecioDocena() {
        return precioDocena;
    }
    
    public void setPrecioDocena(double precioDocena) {
        this.precioDocena = precioDocena;
    }
    
}
