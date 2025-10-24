/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_01_2;

/**
 *
 * @author Pavilion
 */
public class CalculadoraInversion {
    // Calcula el monto para cada rubro
    public void calcularMontos(Rubro[] rubros, double inversionTotal) {
        for (Rubro rubro : rubros) {
            rubro.calcularMonto(inversionTotal);
        }
    }
    
    // Verifica que los porcentajes sumen 100%
    public boolean verificarPorcentajes(Rubro[] rubros) {
        double suma = 0.0;
        for (Rubro rubro : rubros) {
            suma += rubro.getPorcentaje();
        }
        return Math.abs(suma - 100.0) < 0.01; // Tolerancia para decimales
    }
    
    // Calcula el total distribuido
    public double calcularTotalDistribuido(Rubro[] rubros) {
        double total = 0.0;
        for (Rubro rubro : rubros) {
            total += rubro.getMonto();
        }
        return total;
    }
    
}
