/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_01_1;

/**
 *
 * @author Pavilion
 */
public class Calculadora {
    private static final double PORCENTAJE_DESCUENTO = 0.07; // 7%
    
    // Calcula el primer descuento (7% del importe)
    public double calcularPrimerDescuento(double importe) {
        return importe * PORCENTAJE_DESCUENTO;
    }
    
    // Calcula el segundo descuento (7% de lo que queda)
    public double calcularSegundoDescuento(double importe, double primerDescuento) {
        double importeDespuesPrimerDescuento = importe - primerDescuento;
        return importeDespuesPrimerDescuento * PORCENTAJE_DESCUENTO;
    }
    
    // Calcula el descuento total
    public double calcularDescuentoTotal(double primerDescuento, double segundoDescuento) {
        return primerDescuento + segundoDescuento;
    }
}
