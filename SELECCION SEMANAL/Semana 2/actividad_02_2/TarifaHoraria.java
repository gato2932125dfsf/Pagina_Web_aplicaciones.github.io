/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_2;

/**
 *
 * @author Pavilion
 */
public class TarifaHoraria {
     private static final double TARIFA_CATEGORIA_A = 45.0;
    private static final double TARIFA_CATEGORIA_B = 37.5;
    
    // Obtiene la tarifa según la categoría
    public double obtenerTarifa(char categoria) {
        switch (categoria) {
            case 'A':
                return TARIFA_CATEGORIA_A;
            case 'B':
                return TARIFA_CATEGORIA_B;
            default:
                throw new IllegalArgumentException("Categoría inválida. Use 'A' o 'B'.");
        }
    }
    
    // Obtiene la descripción de la tarifa
    public String obtenerDescripcionTarifa(char categoria) {
        return String.format("Categoría %c - S/ %.2f por hora", categoria, obtenerTarifa(categoria));
    }
}
