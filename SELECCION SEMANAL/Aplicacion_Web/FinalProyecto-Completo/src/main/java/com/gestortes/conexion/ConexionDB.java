package com.gestortes.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para gestionar la conexión a la base de datos.
 */
public class ConexionDB {
    
    // --- Configuración de la Conexión ---
    // Asegúrate de que la base de datos "gestion_tesis_db" exista
    // y que el usuario y contraseña sean correctos.
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gestion_tesis_db?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root"; // Cambia esto por tu usuario de MySQL
    private static final String JDBC_PASS = "HYu#qwq#$=($#4_#0233R457<8/%4o3)"; // Cambia esto por tu contraseña de MySQL
    
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Establece y retorna la conexión a la base de datos.
     * @return Objeto Connection
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver JDBC: " + e.getMessage());
            throw new SQLException("Error al cargar el driver", e);
        }
        
        // Establecer la conexión
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    /**
     * Cierra la conexión.
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}