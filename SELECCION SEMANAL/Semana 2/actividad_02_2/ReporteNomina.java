/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_02_2;

/**
 *
 * @author Pavilion
 */
public class ReporteNomina {
    // Muestra el reporte completo de nómina
    public void mostrarReporteCompleto(Nomina nomina) {
        Empleado emp = nomina.getEmpleado();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              REPORTE DE NÓMINA - EMPLEADO                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        System.out.println("\n👤 DATOS DEL EMPLEADO:");
        System.out.println("─".repeat(60));
        System.out.printf("Nombre: %s%n", emp.getNombre());
        System.out.printf("Categoría: %c%n", emp.getCategoria());
        System.out.printf("Tarifa horaria: S/ %.2f%n", nomina.obtenerTarifaHoraria());
        System.out.printf("Horas trabajadas: %.2f horas%n", emp.getHorasTrabajadas());
        System.out.printf("Número de hijos: %d%n", emp.getNumeroHijos());
        
        System.out.println("\n💰 CÁLCULO DE SUELDO:");
        System.out.println("─".repeat(60));
        System.out.printf("Sueldo básico:            S/ %,10.2f%n", nomina.calcularSueldoBasico());
        
        if (emp.getNumeroHijos() > 0) {
            System.out.printf("Bonificación por hijos:   S/ %,10.2f%n", nomina.calcularBonificacionHijos());
            System.out.printf("  (%d hijo(s) × S/ %.2f)%n", 
                emp.getNumeroHijos(), nomina.obtenerMontoPorHijo());
        } else {
            System.out.printf("Bonificación por hijos:   S/ %,10.2f%n", 0.0);
        }
        
        System.out.println("─".repeat(60));
        System.out.printf("SUELDO BRUTO:             S/ %,10.2f%n", nomina.calcularSueldoBruto());
        
        System.out.println("\n📉 DESCUENTOS:");
        System.out.println("─".repeat(60));
        System.out.printf("Descuento (%.1f%%):         S/ %,10.2f%n", 
            nomina.obtenerPorcentajeDescuento(), nomina.calcularDescuento());
        
        System.out.println("\n" + "═".repeat(60));
        System.out.printf("💵 SUELDO NETO:           S/ %,10.2f%n", nomina.calcularSueldoNeto());
        System.out.println("═".repeat(60));
    }
    
    // Muestra un reporte resumido
    public void mostrarReporteResumido(Nomina nomina) {
        Empleado emp = nomina.getEmpleado();
        System.out.printf("%-20s | Cat: %c | Hrs: %6.1f | Hijos: %d | ",
            emp.getNombre(), emp.getCategoria(), emp.getHorasTrabajadas(), emp.getNumeroHijos());
        System.out.printf("Bruto: S/ %,8.2f | Neto: S/ %,8.2f%n",
            nomina.calcularSueldoBruto(), nomina.calcularSueldoNeto());
    }
    
    // Muestra una tabla comparativa
    public void mostrarTablaComparativa(Nomina[] nominas) {
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                      TABLA COMPARATIVA DE NÓMINAS                                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════╝\n");
        
        System.out.printf("%-20s %4s %6s %6s %12s %12s %10s %12s%n",
            "Nombre", "Cat", "Horas", "Hijos", "S. Básico", "S. Bruto", "Desc(%)", "S. Neto");
        System.out.println("─".repeat(86));
        
        double totalBruto = 0;
        double totalNeto = 0;
        
        for (Nomina nomina : nominas) {
            Empleado emp = nomina.getEmpleado();
            System.out.printf("%-20s  %c   %6.1f %6d  S/ %,8.2f  S/ %,8.2f   %5.1f%%  S/ %,8.2f%n",
                emp.getNombre(),
                emp.getCategoria(),
                emp.getHorasTrabajadas(),
                emp.getNumeroHijos(),
                nomina.calcularSueldoBasico(),
                nomina.calcularSueldoBruto(),
                nomina.obtenerPorcentajeDescuento(),
                nomina.calcularSueldoNeto()
            );
            
            totalBruto += nomina.calcularSueldoBruto();
            totalNeto += nomina.calcularSueldoNeto();
        }
        
        System.out.println("═".repeat(86));
        System.out.printf("%-44s S/ %,8.2f             S/ %,8.2f%n",
            "TOTALES:", totalBruto, totalNeto);
        System.out.println("═".repeat(86));
    }
    
    // Muestra estadísticas de la nómina
    public void mostrarEstadisticas(Nomina[] nominas) {
        double sueldoPromedioNeto = 0;
        double sueldoMayorNeto = nominas[0].calcularSueldoNeto();
        double sueldoMenorNeto = nominas[0].calcularSueldoNeto();
        String empleadoMayor = nominas[0].getEmpleado().getNombre();
        String empleadoMenor = nominas[0].getEmpleado().getNombre();
        
        for (Nomina nomina : nominas) {
            double sueldoNeto = nomina.calcularSueldoNeto();
            sueldoPromedioNeto += sueldoNeto;
            
            if (sueldoNeto > sueldoMayorNeto) {
                sueldoMayorNeto = sueldoNeto;
                empleadoMayor = nomina.getEmpleado().getNombre();
            }
            
            if (sueldoNeto < sueldoMenorNeto) {
                sueldoMenorNeto = sueldoNeto;
                empleadoMenor = nomina.getEmpleado().getNombre();
            }
        }
        
        sueldoPromedioNeto /= nominas.length;
        
        System.out.println("\n📊 ESTADÍSTICAS DE NÓMINA:");
        System.out.println("─".repeat(60));
        System.out.printf("Sueldo neto promedio: S/ %,.2f%n", sueldoPromedioNeto);
        System.out.printf("Sueldo neto mayor: S/ %,.2f (%s)%n", sueldoMayorNeto, empleadoMayor);
        System.out.printf("Sueldo neto menor: S/ %,.2f (%s)%n", sueldoMenorNeto, empleadoMenor);
    }
    
}
