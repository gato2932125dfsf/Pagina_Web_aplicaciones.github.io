/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_01_1;

/**
 *
 * @author Pavilion
 */
public class ReporteVenta {
     // Muestra el resumen completo de la venta
    public void mostrarResumen(Venta venta) {
        System.out.println("========================================");
        System.out.println("     RESUMEN DE COMPRA - CAMISAS");
        System.out.println("========================================");
        System.out.printf("Precio unitario: S/ %.2f%n", venta.getCamisa().getPrecio());
        System.out.printf("Cantidad: %d unidades%n", venta.getCantidad());
        System.out.println("----------------------------------------");
        System.out.printf("Importe de compra: S/ %.2f%n", venta.calcularImporteCompra());
        System.out.printf("Primer descuento (7%%): S/ %.2f%n", venta.calcularPrimerDescuento());
        System.out.printf("Segundo descuento (7%%): S/ %.2f%n", venta.calcularSegundoDescuento());
        System.out.printf("Descuento total: S/ %.2f%n", venta.calcularDescuentoTotal());
        System.out.println("========================================");
        System.out.printf("IMPORTE A PAGAR: S/ %.2f%n", venta.calcularImportePagar());
        System.out.println("========================================");
    }
    
    // Muestra un resumen breve
    public void mostrarResumenBreve(Venta venta) {
        System.out.printf("Total: S/ %.2f | Descuento: S/ %.2f | A Pagar: S/ %.2f%n",
            venta.calcularImporteCompra(),
            venta.calcularDescuentoTotal(),
            venta.calcularImportePagar()
        );
    }
}
