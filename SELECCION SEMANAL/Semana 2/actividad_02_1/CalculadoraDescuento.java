/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_1;

/**
 *
 * @author Pavilion
 */
public class CalculadoraDescuento {
    private static final double DESCUENTO_MAYOR = 0.20; // 20% para >= 10 docenas
    private static final double DESCUENTO_MENOR = 0.10; // 10% para < 10 docenas
    private static final int LIMITE_DOCENAS = 10;
    
    // Determina el porcentaje de descuento según las docenas
    public double obtenerPorcentajeDescuento(int docenas) {
        if (docenas >= LIMITE_DOCENAS) {
            return DESCUENTO_MAYOR;
        } else {
            return DESCUENTO_MENOR;
        }
    }
    
    // Calcula el monto del descuento
    public double calcularDescuento(double importeCompra, int docenas) {
        double porcentaje = obtenerPorcentajeDescuento(docenas);
        return importeCompra * porcentaje;
    }
    
    // Obtiene el porcentaje como entero para mostrar
    public int obtenerPorcentajeParaMostrar(int docenas) {
        return (int)(obtenerPorcentajeDescuento(docenas) * 100);
    }
    
}
