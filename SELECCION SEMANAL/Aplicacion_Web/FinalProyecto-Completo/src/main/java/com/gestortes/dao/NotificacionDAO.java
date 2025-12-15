package com.gestortes.dao;

import com.gestortes.conexion.ConexionDB;
import com.gestortes.modelo.Notificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class NotificacionDAO {

    // Listar para ADMINS (usuario_id IS NULL)
    public List<Notificacion> listarNoLeidasAdmin() {
        return listarNoLeidasGenerico("SELECT * FROM notificaciones WHERE leido = FALSE AND usuario_id IS NULL ORDER BY fecha DESC", -1);
    }

    // Listar para un USUARIO ESPECÍFICO (Docente/Alumno)
    public List<Notificacion> listarNoLeidasUsuario(int usuarioId) {
        return listarNoLeidasGenerico("SELECT * FROM notificaciones WHERE leido = FALSE AND usuario_id = ? ORDER BY fecha DESC", usuarioId);
    }

    private List<Notificacion> listarNoLeidasGenerico(String sql, int paramId) {
        List<Notificacion> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (paramId != -1) {
                pstmt.setInt(1, paramId);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Notificacion n = new Notificacion();
                    n.setId(rs.getInt("id"));
                    n.setMensaje(rs.getString("mensaje"));
                    n.setTipo(rs.getString("tipo"));
                    n.setLeido(rs.getBoolean("leido"));
                    n.setFecha(rs.getTimestamp("fecha"));
                    n.setUsuarioId(rs.getInt("usuario_id"));
                    if (rs.wasNull()) n.setUsuarioId(null);
                    lista.add(n);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    // Crear notificación dirigida
    public boolean crearNotificacion(String mensaje, String tipo, Integer usuarioId) {
        String sql = "INSERT INTO notificaciones (mensaje, tipo, fecha, usuario_id) VALUES (?, ?, NOW(), ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mensaje);
            pstmt.setString(2, tipo);
            
            if (usuarioId == null) {
                pstmt.setNull(3, Types.INTEGER);
            } else {
                pstmt.setInt(3, usuarioId);
            }
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
    
    // Marcar leídas para Admin
    public boolean marcarTodasLeidasAdmin() {
        String sql = "UPDATE notificaciones SET leido = TRUE WHERE leido = FALSE AND usuario_id IS NULL";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    // Marcar leídas para Usuario
    public boolean marcarTodasLeidasUsuario(int usuarioId) {
        String sql = "UPDATE notificaciones SET leido = TRUE WHERE leido = FALSE AND usuario_id = ?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}