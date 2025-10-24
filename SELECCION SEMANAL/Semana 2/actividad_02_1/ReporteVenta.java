/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_1;

/**
 *
 * @author Pavilion
 */
public class ReporteVenta {
    // Muestra el reporte completo de la venta
    public void mostrarReporteCompleto(Venta venta) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║           REPORTE DE VENTA - TIENDA                    ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        System.out.println("\n📦 DETALLES DE LA COMPRA:");
        System.out.println("─".repeat(56));
        System.out.printf("Producto: %s%n", venta.getProducto().getNombre());
        System.out.printf("Precio por docena: S/ %.2f%n", venta.getProducto().getPrecioDocena());
        System.out.printf("Cantidad de docenas: %d%n", venta.getDocenasAdquiridas());
        
        System.out.println("\n💰 CÁLCULOS FINANCIEROS:");
        System.out.println("─".repeat(56));
        System.out.printf("Importe de compra:        S/ %,10.2f%n", venta.calcularImporteCompra());
        System.out.printf("Descuento (%d%%):           S/ %,10.2f%n", 
            venta.obtenerPorcentajeDescuento(), venta.calcularImporteDescuento());
        System.out.println("─".repeat(56));
        System.out.printf("IMPORTE A PAGAR:          S/ %,10.2f%n", venta.calcularImportePagar());
        
        System.out.println("\n🎁 OBSEQUIOS:");
        System.out.println("─".repeat(56));
        int lapiceros = venta.calcularLapicerosObsequio();
        if (lapiceros > 0) {
            System.out.printf("Lapiceros de obsequio: %d unidades%n", lapiceros);
            System.out.println("✓ Califica para obsequio (Importe >= S/ 200)");
        } else {
            System.out.println("Lapiceros de obsequio: 0 unidades");
            System.out.println("✗ No califica para obsequio (Importe < S/ 200)");
        }
        
        System.out.println("═".repeat(56));
    }
    
    // Muestra un reporte resumido en una línea
    public void mostrarReporteResumido(Venta venta) {
        System.out.printf("Docenas: %d | Importe: S/ %.2f | Descuento: %.0f%% | ",
            venta.getDocenasAdquiridas(),
            venta.calcularImportePagar(),
            venta.obtenerPorcentajeDescuento() * 1.0
        );
        System.out.printf("A Pagar: S/ %.2f | Lapiceros: %d%n",
            venta.calcularImportePagar(),
            venta.calcularLapicerosObsequio()
        );
    }
    
    // Muestra una tabla comparativa
    public void mostrarTablaComparativa(Venta[] ventas) {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    TABLA COMPARATIVA DE VENTAS                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════╝\n");
        
        System.out.printf("%-10s %-12s %-12s %-12s %-12s %-10s%n",
            "Docenas", "Compra", "Desc(%)", "Descuento", "A Pagar", "Lapiceros");
        System.out.println("─".repeat(72));
        
        for (Venta venta : ventas) {
            System.out.printf("%-10d S/ %,8.2f  %4d%%    S/ %,8.2f  S/ %,8.2f  %6d%n",
                venta.getDocenasAdquiridas(),
                venta.calcularImporteCompra(),
                venta.obtenerPorcentajeDescuento(),
                venta.calcularImporteDescuento(),
                venta.calcularImportePagar(),
                venta.calcularLapicerosObsequio()
            );
        }
        System.out.println("═".repeat(72));
    }
}
