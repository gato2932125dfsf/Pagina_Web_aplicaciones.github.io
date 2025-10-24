/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_01_1;

/**
 *
 * @author Pavilion
 */
public class Camisa {
    private double precio;
    private String talla;
    private String color;
    
    // Constructor
    public Camisa(double precio) {
        this.precio = precio;
    }
    
    public Camisa(double precio, String talla, String color) {
        this.precio = precio;
        this.talla = talla;
        this.color = color;
    }
    
    // Getters y Setters
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public String getTalla() {
        return talla;
    }
    
    public void setTalla(String talla) {
        this.talla = talla;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
}

