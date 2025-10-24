/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.zonajava.actividad_01_2;

/**
 *
 * @author Pavilion
 */
public class Actividad_01_2 {

    public static void main(String[] args) {
        ReporteInversion reporte = new ReporteInversion();
        
        // Ejemplo 1: Inversión de S/ 50,000
        System.out.println("\n" + "═".repeat(60));
        System.out.println("              EJEMPLO 1");
        System.out.println("═".repeat(60));
        InversionFeria inversion1 = new InversionFeria(50000.00);
        inversion1.procesarInversion();
        reporte.mostrarReporteCompleto(inversion1);
        reporte.mostrarTop3Rubros(inversion1);
        
        // Ejemplo 2: Inversión de S/ 120,000
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("              EJEMPLO 2");
        System.out.println("═".repeat(60));
        InversionFeria inversion2 = new InversionFeria(120000.00);
        inversion2.procesarInversion();
        reporte.mostrarReporteCompleto(inversion2);
        
        // Ejemplo 3: Modificación de monto total
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("              EJEMPLO 3");
        System.out.println("═".repeat(60));
        InversionFeria inversion3 = new InversionFeria(75000.00);
        inversion3.procesarInversion();
        
        System.out.println("Inversión inicial:");
        reporte.mostrarReporteResumido(inversion3);
        
        // Cambiar el monto total
        inversion3.setMontoTotal(85000.00);
        System.out.println("\nDespués de ajustar la inversión:");
        reporte.mostrarReporteResumido(inversion3);
        
        // Ejemplo 4: Comparación de múltiples escenarios
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("         EJEMPLO 4: COMPARACIÓN DE ESCENARIOS");
        System.out.println("═".repeat(60));
        
        double[] escenarios = {30000, 60000, 100000};
        for (double monto : escenarios) {
            InversionFeria inv = new InversionFeria(monto);
            inv.procesarInversion();
            reporte.mostrarReporteResumido(inv);
        }
    }
}
