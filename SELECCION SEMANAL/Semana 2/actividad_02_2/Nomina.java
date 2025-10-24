/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_2;

/**
 *
 * @author Pavilion
 */
public class Nomina {
     private Empleado empleado;
    private TarifaHoraria tarifaHoraria;
    private CalculadoraBonificadora calculadoraBonificacion;
    private CalculadoraDescuento calculadoraDescuento;
    
    // Constructor
    public Nomina(Empleado empleado) {
        this.empleado = empleado;
        this.tarifaHoraria = new TarifaHoraria();
        this.calculadoraBonificacion = new CalculadoraBonificadora();
        this.calculadoraDescuento = new CalculadoraDescuento();
    }
    
    // Getters
    public Empleado getEmpleado() {
        return empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    // Obtiene la tarifa horaria del empleado
    public double obtenerTarifaHoraria() {
        return tarifaHoraria.obtenerTarifa(empleado.getCategoria());
    }
    
    // Calcula el sueldo básico
    public double calcularSueldoBasico() {
        return empleado.getHorasTrabajadas() * obtenerTarifaHoraria();
    }
    
    // Calcula la bonificación por hijos
    public double calcularBonificacionHijos() {
        return calculadoraBonificacion.calcularBonificacion(empleado.getNumeroHijos());
    }
    
    // Calcula el sueldo bruto
    public double calcularSueldoBruto() {
        return calcularSueldoBasico() + calcularBonificacionHijos();
    }
    
    // Calcula el descuento
    public double calcularDescuento() {
        return calculadoraDescuento.calcularDescuento(calcularSueldoBruto());
    }
    
    // Calcula el sueldo neto
    public double calcularSueldoNeto() {
        return calcularSueldoBruto() - calcularDescuento();
    }
    
    // Obtiene el porcentaje de descuento aplicado
    public double obtenerPorcentajeDescuento() {
        return calculadoraDescuento.obtenerPorcentajeParaMostrar(calcularSueldoBruto());
    }
    
    // Obtiene el monto de bonificación por hijo
    public double obtenerMontoPorHijo() {
        return calculadoraBonificacion.obtenerMontoPorHijo(empleado.getNumeroHijos());
    }
    
}
