/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_2;

/**
 *
 * @author Pavilion
 */
public class CalculadoraBonificadora {
    private static final double BONIFICACION_HASTA_3 = 40.5;
    private static final double BONIFICACION_MAS_DE_3 = 35.0;
    private static final int LIMITE_HIJOS = 3;
    
    // Calcula la bonificación por hijos
    public double calcularBonificacion(int numeroHijos) {
        if (numeroHijos == 0) {
            return 0.0;
        }
        
        if (numeroHijos <= LIMITE_HIJOS) {
            return numeroHijos * BONIFICACION_HASTA_3;
        } else {
            return numeroHijos * BONIFICACION_MAS_DE_3;
        }
    }
    
    // Obtiene el monto de bonificación por hijo según la cantidad
    public double obtenerMontoPorHijo(int numeroHijos) {
        if (numeroHijos <= LIMITE_HIJOS) {
            return BONIFICACION_HASTA_3;
        } else {
            return BONIFICACION_MAS_DE_3;
        }
    }
    
}
