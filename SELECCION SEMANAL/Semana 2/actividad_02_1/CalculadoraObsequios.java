/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_1;

/**
 *
 * @author Pavilion
 */
public class CalculadoraObsequios {
    private static final double MONTO_MINIMO_OBSEQUIO = 200.0;
    private static final int LAPICEROS_POR_DOCENA = 2;
    
    // Calcula la cantidad de lapiceros de obsequio
    public int calcularLapicerosObsequio(double importePagar, int docenas) {
        if (importePagar >= MONTO_MINIMO_OBSEQUIO) {
            return docenas * LAPICEROS_POR_DOCENA;
        } else {
            return 0;
        }
    }
    
    // Verifica si califica para obsequio
    public boolean calificaParaObsequio(double importePagar) {
        return importePagar >= MONTO_MINIMO_OBSEQUIO;
    }
}
