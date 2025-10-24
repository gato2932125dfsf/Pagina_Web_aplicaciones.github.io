/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_01_2;

/**
 *
 * @author Pavilion
 */
public class Rubro {
    private String nombre;
    private double porcentaje;
    private double monto;
    
    // Constructor
    public Rubro(String nombre, double porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
        this.monto = 0.0;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public double getPorcentaje() {
        return porcentaje;
    }
    
    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    public double getMonto() {
        return monto;
    }
    
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    // Calcula el monto asignado según el porcentaje
    public void calcularMonto(double inversionTotal) {
        this.monto = inversionTotal * (porcentaje / 100.0);
    }
    
    @Override
    public String toString() {
        return String.format("%-30s %6.1f%%   S/ %,10.2f", 
            nombre, porcentaje, monto);
    }
    
}
