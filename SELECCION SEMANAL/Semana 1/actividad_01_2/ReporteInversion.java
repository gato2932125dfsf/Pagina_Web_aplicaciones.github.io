/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zonajava.actividad_01_2;

/**
 *
 * @author Pavilion
 */
public class ReporteInversion {
    public void mostrarReporteCompleto(InversionFeria inversion) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║        DISTRIBUCIÓN DE INVERSIÓN EN FERIA                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.printf("\n💰 INVERSIÓN TOTAL: S/ %,.2f\n", inversion.getMontoTotal());
        System.out.println("\n" + "─".repeat(60));
        System.out.printf("%-30s %8s   %12s%n", "RUBRO", "%", "MONTO");
        System.out.println("─".repeat(60));
        
        for (Rubro rubro : inversion.getRubros()) {
            System.out.println(rubro);
        }
        
        System.out.println("─".repeat(60));
        System.out.printf("%-30s %8s   S/ %,10.2f%n", 
            "TOTAL DISTRIBUIDO", "100.0%", inversion.getTotalDistribuido());
        System.out.println("═".repeat(60));
        
        // Verificación
        if (inversion.verificarDistribucion()) {
            System.out.println("✓ Distribución verificada correctamente");
        } else {
            System.out.println("⚠ Advertencia: Los porcentajes no suman 100%");
        }
    }
    
    // Muestra un reporte resumido
    public void mostrarReporteResumido(InversionFeria inversion) {
        System.out.printf("\nInversión Total: S/ %,.2f | ", inversion.getMontoTotal());
        System.out.printf("Rubro Mayor: %s (S/ %,.2f)%n",
            obtenerRubroMayor(inversion).getNombre(),
            obtenerRubroMayor(inversion).getMonto()
        );
    }
    
    // Obtiene el rubro con mayor inversión
    private Rubro obtenerRubroMayor(InversionFeria inversion) {
        Rubro mayor = inversion.getRubros()[0];
        for (Rubro rubro : inversion.getRubros()) {
            if (rubro.getMonto() > mayor.getMonto()) {
                mayor = rubro;
            }
        }
        return mayor;
    }
    
    // Muestra solo los rubros más importantes (top 3)
    public void mostrarTop3Rubros(InversionFeria inversion) {
        Rubro[] rubros = inversion.getRubros().clone();
        
        // Ordenar por monto (burbuja simple)
        for (int i = 0; i < rubros.length - 1; i++) {
            for (int j = 0; j < rubros.length - i - 1; j++) {
                if (rubros[j].getMonto() < rubros[j + 1].getMonto()) {
                    Rubro temp = rubros[j];
                    rubros[j] = rubros[j + 1];
                    rubros[j + 1] = temp;
                }
            }
        }
        
        System.out.println("\n🏆 TOP 3 RUBROS CON MAYOR INVERSIÓN:");
        for (int i = 0; i < 3 && i < rubros.length; i++) {
            System.out.printf("%d. %s - S/ %,.2f (%.1f%%)%n",
                i + 1, rubros[i].getNombre(), rubros[i].getMonto(), rubros[i].getPorcentaje());
        }
    }
    
}
