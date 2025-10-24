/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.zonajava.actividad_03_2;
import java.util.Scanner;
/**
 *
 * @author Pavilion
 */
public class Actividad_03_2 {
 public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el turno (mañana/noche): ");
        String turno = sc.nextLine();

        System.out.print("Ingrese la cantidad de pasajes: ");
        int cantidad = sc.nextInt();

        // Crear objeto Pasaje
        Pasaje p = new Pasaje(turno, cantidad);

        // Mostrar resultados
        System.out.println("\n--- RESULTADOS ---");
        System.out.println("Turno: " + p.getTurno());
        System.out.println("Importe de la compra: " + p.calcularImporteCompra());
        System.out.println("Descuento: " + p.calcularDescuento());
        System.out.println("Importe a pagar: " + p.calcularImportePagar());
        System.out.println("Caramelos de obsequio: " + p.calcularCaramelos());

        sc.close();
    }
}
