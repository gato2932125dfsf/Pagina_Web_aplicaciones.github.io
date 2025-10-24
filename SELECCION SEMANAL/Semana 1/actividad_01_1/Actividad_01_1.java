/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.zonajava.actividad_01_1;

/**
 *
 * @author Pavilion
 */
public class Actividad_01_1 {

    public static void main(String[] args) {
        
        ReporteVenta reporte = new ReporteVenta();
        
        // Ejemplo 1: Venta de 5 camisas a S/ 80.00
        System.out.println("\n===== EJEMPLO 1 =====");
        Camisa camisa1 = new Camisa(80.00);
        Venta venta1 = new Venta(camisa1, 5);
        reporte.mostrarResumen(venta1);
        
        // Ejemplo 2: Venta de 3 camisas a S/ 120.50
        System.out.println("\n\n===== EJEMPLO 2 =====");
        Camisa camisa2 = new Camisa(120.50, "L", "Azul");
        Venta venta2 = new Venta(camisa2, 3);
        reporte.mostrarResumen(venta2);
        
        // Ejemplo 3: Venta con modificación de datos
        System.out.println("\n\n===== EJEMPLO 3 =====");
        Camisa camisa3 = new Camisa(100.00, "M", "Blanco");
        Venta venta3 = new Venta(camisa3, 2);
        
        // Modificar precio y cantidad
        camisa3.setPrecio(95.00);
        venta3.setCantidad(4);
        reporte.mostrarResumen(venta3);
        
        // Ejemplo 4: Resumen breve de múltiples ventas
        System.out.println("\n\n===== EJEMPLO 4: RESUMEN BREVE =====");
        reporte.mostrarResumenBreve(venta1);
        reporte.mostrarResumenBreve(venta2);
        reporte.mostrarResumenBreve(venta3);
    }
    
}
