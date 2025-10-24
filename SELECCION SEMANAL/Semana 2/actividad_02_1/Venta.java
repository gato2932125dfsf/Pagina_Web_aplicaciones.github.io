/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_1;

/**
 *
 * @author Pavilion
 */
public class Venta {
    private Producto producto;
    private int docenasAdquiridas;
    private CalculadoraDescuento calculadoraDescuento;
    private CalculadoraObsequios calculadoraObsequios;
    
    // Constructor
    public Venta(Producto producto, int docenasAdquiridas) {
        this.producto = producto;
        this.docenasAdquiridas = docenasAdquiridas;
        this.calculadoraDescuento = new CalculadoraDescuento();
        this.calculadoraObsequios = new CalculadoraObsequios();
    }
    
    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    public int getDocenasAdquiridas() {
        return docenasAdquiridas;
    }
    
    public void setDocenasAdquiridas(int docenasAdquiridas) {
        this.docenasAdquiridas = docenasAdquiridas;
    }
    
    // Calcula el importe de la compra (antes del descuento)
    public double calcularImporteCompra() {
        return producto.getPrecioDocena() * docenasAdquiridas;
    }
    
    // Calcula el importe del descuento
    public double calcularImporteDescuento() {
        return calculadoraDescuento.calcularDescuento(
            calcularImporteCompra(), 
            docenasAdquiridas
        );
    }
    
    // Calcula el importe a pagar (después del descuento)
    public double calcularImportePagar() {
        return calcularImporteCompra() - calcularImporteDescuento();
    }
    
    // Obtiene el porcentaje de descuento aplicado
    public int obtenerPorcentajeDescuento() {
        return calculadoraDescuento.obtenerPorcentajeParaMostrar(docenasAdquiridas);
    }
    
    // Calcula la cantidad de lapiceros de obsequio
    public int calcularLapicerosObsequio() {
        return calculadoraObsequios.calcularLapicerosObsequio(
            calcularImportePagar(), 
            docenasAdquiridas
        );
    }
    
    // Verifica si califica para obsequio
    public boolean calificaParaObsequio() {
        return calculadoraObsequios.calificaParaObsequio(calcularImportePagar());
    }
}
