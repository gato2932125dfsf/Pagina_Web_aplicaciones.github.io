/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.zonajava.actividad_02_2;

/**
 *
 * @author Pavilion
 */
public class Actividad_02_2 {

    public static void main(String[] args) {
         ReporteNomina reporte = new ReporteNomina();
        
        // Ejemplo 1: Empleado categoría A con 2 hijos
        System.out.println("\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 1: Categoría A - 2 hijos");
        System.out.println("═".repeat(60));
        Empleado emp1 = new Empleado("Juan Pérez", 'A', 160, 2);
        Nomina nomina1 = new Nomina(emp1);
        reporte.mostrarReporteCompleto(nomina1);
        
        // Ejemplo 2: Empleado categoría B con 4 hijos
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 2: Categoría B - 4 hijos");
        System.out.println("═".repeat(60));
        Empleado emp2 = new Empleado("María García", 'B', 180, 4);
        Nomina nomina2 = new Nomina(emp2);
        reporte.mostrarReporteCompleto(nomina2);
        
        // Ejemplo 3: Empleado sin hijos
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 3: Categoría A - Sin hijos");
        System.out.println("═".repeat(60));
        Empleado emp3 = new Empleado("Carlos López", 'A', 120, 0);
        Nomina nomina3 = new Nomina(emp3);
        reporte.mostrarReporteCompleto(nomina3);
        
        // Ejemplo 4: Tabla comparativa
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 4: Comparación de múltiples empleados");
        System.out.println("═".repeat(60));
        
        Nomina[] nominas = {
            new Nomina(new Empleado("Ana Torres", 'A', 160, 2)),
            new Nomina(new Empleado("Luis Ramos", 'B', 170, 3)),
            new Nomina(new Empleado("Sofia Vega", 'A', 200, 1)),
            new Nomina(new Empleado("Pedro Ruiz", 'B', 150, 5)),
            new Nomina(new Empleado("Laura Castro", 'A', 180, 0))
        };
        
        reporte.mostrarTablaComparativa(nominas);
        reporte.mostrarEstadisticas(nominas);
        
        // Ejemplo 5: Modificación dinámica
        System.out.println("\n\n" + "═".repeat(60));
        System.out.println("    EJEMPLO 5: Modificación de datos");
        System.out.println("═".repeat(60));
        
        Empleado emp5 = new Empleado("Roberto Díaz", 'B', 140, 2);
        Nomina nomina5 = new Nomina(emp5);
        
        System.out.println("\nNómina inicial:");
        reporte.mostrarReporteResumido(nomina5);
        
        // Cambiar categoría y horas
        emp5.setCategoria('A');
        emp5.setHorasTrabajadas(160);
        emp5.setNumeroHijos(3);
        
        System.out.println("\nDespués de modificaciones:");
        reporte.mostrarReporteResumido(nomina5);
        
        System.out.println("\n");
    }
}
