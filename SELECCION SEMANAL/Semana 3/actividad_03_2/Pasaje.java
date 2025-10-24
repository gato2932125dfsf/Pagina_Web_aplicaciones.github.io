/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_03_2;

/**
 *
 * @author Pavilion
 */
public class Pasaje {
     private String turno;
    private int cantidad;

    // Precio fijo del pasaje
    private final double PRECIO = 37.5;

    public Pasaje(String turno, int cantidad) {
        this.turno = turno;
        this.cantidad = cantidad;
    }

    public double calcularImporteCompra() {
        return PRECIO * cantidad;
    }

    public double calcularDescuento() {
        if (cantidad >= 15) {
            return calcularImporteCompra() * 0.08;
        } else {
            return calcularImporteCompra() * 0.05;
        }
    }

    public double calcularImportePagar() {
        return calcularImporteCompra() - calcularDescuento();
    }

    public int calcularCaramelos() {
        if (calcularImportePagar() > 200) {
            return cantidad * 2;
        } else {
            return 0;
        }
    }

    // Para mostrar datos adicionales si quieres
    public String getTurno() {
        return turno;
    }
    
}
