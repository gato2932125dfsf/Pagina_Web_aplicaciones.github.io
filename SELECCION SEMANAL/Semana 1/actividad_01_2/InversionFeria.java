/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_01_2;

/**
 *
 * @author Pavilion
 */
public class InversionFeria {
    private double montoTotal;
    private Rubro[] rubros;
    private CalculadoraInversion calculadora;
    
    // Constructor
    public InversionFeria(double montoTotal) {
        this.montoTotal = montoTotal;
        this.calculadora = new CalculadoraInversion();
        inicializarRubros();
    }
    
    // Inicializa los rubros con los porcentajes establecidos
    private void inicializarRubros() {
        rubros = new Rubro[6];
        rubros[0] = new Rubro("Alquiler de espacio", 23.0);
        rubros[1] = new Rubro("Publicidad", 7.0);
        rubros[2] = new Rubro("Transporte", 26.0);
        rubros[3] = new Rubro("Servicios feriales", 12.0);
        rubros[4] = new Rubro("Decoración", 21.0);
        rubros[5] = new Rubro("Gastos varios", 11.0);
    }
    
    // Getters y Setters
    public double getMontoTotal() {
        return montoTotal;
    }
    
    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
        procesarInversion();
    }
    
    public Rubro[] getRubros() {
        return rubros;
    }
    
    // Procesa la inversión calculando todos los montos
    public void procesarInversion() {
        calculadora.calcularMontos(rubros, montoTotal);
    }
    
    // Verifica la consistencia de los porcentajes
    public boolean verificarDistribucion() {
        return calculadora.verificarPorcentajes(rubros);
    }
    
    // Obtiene el total distribuido
    public double getTotalDistribuido() {
        return calculadora.calcularTotalDistribuido(rubros);
    }
}
    

