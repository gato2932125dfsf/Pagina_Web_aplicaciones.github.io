package com.gestortes.dao;

import com.gestortes.conexion.ConexionDB;
import com.gestortes.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO para gestionar las operaciones de usuarios (Login).
 * Utiliza la vista "vista_login_usuarios".
 */
public class UsuarioDAO {

    /**
     * Valida un usuario contra la base de datos usando la vista unificada.
     * @param loginId (codigo_usuario en la vista: DNI, Código o Email)
     * @param password (contraseña en texto plano, como está en tu BD)
     * @return Un objeto Usuario si es válido, null si no lo es.
     */
    public Usuario validarUsuario(String loginId, String password) {
        
        // Usamos la vista que creamos en el script SQL
        String sql = "SELECT * FROM vista_login_usuarios WHERE codigo_usuario = ? AND password = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            conn = ConexionDB.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, loginId);
            pstmt.setString(2, password); // NOTA: En producción, esto debería ser un HASH
            
            rs = pstmt.executeQuery();

            // Si encontramos un resultado, creamos el objeto Usuario
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setRol(rs.getString("rol"));
                usuario.setCodigoUsuario(rs.getString("codigo_usuario"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
            // Manejar la excepción (ej. log)
        } finally {
            // Cerramos todos los recursos
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
            ConexionDB.close(conn);
        }
        
        return usuario; // Retorna el usuario (o null si no se encontró)
    }
}