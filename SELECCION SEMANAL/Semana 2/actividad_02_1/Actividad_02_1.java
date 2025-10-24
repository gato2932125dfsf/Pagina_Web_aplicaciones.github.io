/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.zonajava.actividad_02_1;

/**
 *
 * @author Pavilion
 */
public class Actividad_02_1 {

    public static void main(String[] args) {
        ReporteVenta reporte = new ReporteVenta();
        
        // Ejemplo 1: Compra de 5 docenas (menos de 10)
        System.out.println("\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 1: Compra de 5 docenas (Descuento 10%)");
        System.out.println("═".repeat(60));
        Producto producto1 = new Producto("Cuadernos", 30.00);
        Venta venta1 = new Venta(producto1, 5);
        reporte.mostrarReporteCompleto(venta1);
        
        // Ejemplo 2: Compra de 12 docenas (10 o más)
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 2: Compra de 12 docenas (Descuento 20%)");
        System.out.println("═".repeat(60));
        Producto producto2 = new Producto("Lapiceros", 25.00);
        Venta venta2 = new Venta(producto2, 12);
        reporte.mostrarReporteCompleto(venta2);
        
        // Ejemplo 3: Compra en el límite (10 docenas)
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 3: Compra de 10 docenas (Límite)");
        System.out.println("═".repeat(60));
        Producto producto3 = new Producto("Borradores", 15.00);
        Venta venta3 = new Venta(producto3, 10);
        reporte.mostrarReporteCompleto(venta3);
        
        // Ejemplo 4: Compra que NO califica para obsequio
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 4: Compra sin obsequio");
        System.out.println("═".repeat(60));
        Producto producto4 = new Producto("Reglas", 12.00);
        Venta venta4 = new Venta(producto4, 8);
        reporte.mostrarReporteCompleto(venta4);
        
        // Ejemplo 5: Tabla comparativa de múltiples ventas
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 5: Comparación de diferentes escenarios");
        System.out.println("═".repeat(60));
        
        Producto productoComparacion = new Producto("Producto General", 20.00);
        Venta[] ventas = {
            new Venta(productoComparacion, 3),
            new Venta(productoComparacion, 8),
            new Venta(productoComparacion, 10),
            new Venta(productoComparacion, 15),
            new Venta(productoComparacion, 20)
        };
        
        reporte.mostrarTablaComparativa(ventas);
        
        // Ejemplo 6: Modificación dinámica
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 6: Modificación de cantidad");
        System.out.println("═".repeat(60));
        
        Producto producto6 = new Producto("Tijeras", 18.00);
        Venta venta6 = new Venta(producto6, 7);
        
        System.out.println("\nVenta inicial:");
        reporte.mostrarReporteResumido(venta6);
        
        // Aumentar la cantidad
        venta6.setDocenasAdquiridas(12);
        System.out.println("\nDespués de aumentar a 12 docenas:");
        reporte.mostrarReporteResumido(venta6);
        
        System.out.println("\n");
    }
}
