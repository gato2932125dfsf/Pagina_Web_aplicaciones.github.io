/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_01_1;

/**
 *
 * @author Pavilion
 */
public class Venta {
    private Camisa camisa;
    private int cantidad;
    private Calculadora calculadora;
    
    // Constructor
    public Venta(Camisa camisa, int cantidad) {
        this.camisa = camisa;
        this.cantidad = cantidad;
        this.calculadora = new Calculadora();
    }
    
    // Getters y Setters
    public Camisa getCamisa() {
        return camisa;
    }
    
    public void setCamisa(Camisa camisa) {
        this.camisa = camisa;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    // Calcula el importe de la compra
    public double calcularImporteCompra() {
        return camisa.getPrecio() * cantidad;
    }
    
    // Calcula el primer descuento
    public double calcularPrimerDescuento() {
        return calculadora.calcularPrimerDescuento(calcularImporteCompra());
    }
    
    // Calcula el segundo descuento
    public double calcularSegundoDescuento() {
        return calculadora.calcularSegundoDescuento(
            calcularImporteCompra(), 
            calcularPrimerDescuento()
        );
    }
    
    // Calcula el descuento total
    public double calcularDescuentoTotal() {
        return calculadora.calcularDescuentoTotal(
            calcularPrimerDescuento(), 
            calcularSegundoDescuento()
        );
    }
    
    // Calcula el importe a pagar
    public double calcularImportePagar() {
        return calcularImporteCompra() - calcularDescuentoTotal();
    }
}
