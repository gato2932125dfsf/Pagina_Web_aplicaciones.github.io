/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_2;

/**
 *
 * @author Pavilion
 */
public class CalculadoraDescuento {
     private static final double DESCUENTO_MAYOR = 0.135; // 13.5%
    private static final double DESCUENTO_MENOR = 0.10;  // 10.0%
    private static final double LIMITE_SUELDO = 3500.0;
    
    // Obtiene el porcentaje de descuento según el sueldo bruto
    public double obtenerPorcentajeDescuento(double sueldoBruto) {
        if (sueldoBruto >= LIMITE_SUELDO) {
            return DESCUENTO_MAYOR;
        } else {
            return DESCUENTO_MENOR;
        }
    }
    
    // Calcula el monto del descuento
    public double calcularDescuento(double sueldoBruto) {
        return sueldoBruto * obtenerPorcentajeDescuento(sueldoBruto);
    }
    
    // Obtiene el porcentaje como número entero para mostrar
    public double obtenerPorcentajeParaMostrar(double sueldoBruto) {
        return obtenerPorcentajeDescuento(sueldoBruto) * 100;
    }
    
}
