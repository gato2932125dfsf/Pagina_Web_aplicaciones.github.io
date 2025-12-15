package com.gestortes.dao;

import com.gestortes.conexion.ConexionDB;
import com.gestortes.modelo.Alumno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    public List<Alumno> listarAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT id, nombre, email, codigo FROM alumnos";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setEmail(rs.getString("email"));
                alumno.setCodigo(rs.getString("codigo"));
                alumnos.add(alumno);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return alumnos;
    }
    
    public Alumno buscarPorId(int id) { /* ... (sin cambios, igual que antes) ... */ return null; } // Simplificado aquí para brevedad, usa el que tenías

    public boolean crearAlumno(Alumno alumno) {
        String sql = "INSERT INTO alumnos (nombre, email, codigo, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, alumno.getNombre());
            pstmt.setString(2, alumno.getEmail());
            pstmt.setString(3, alumno.getCodigo());
            pstmt.setString(4, alumno.getPassword());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
    
    // --- MÉTODO ACTUALIZADO CON PASSWORD OPCIONAL ---
    public boolean actualizarAlumno(Alumno alumno) {
        // Si el password es nulo o vacío, no lo actualizamos
        boolean cambiarPass = alumno.getPassword() != null && !alumno.getPassword().trim().isEmpty();
        
        String sql = cambiarPass 
            ? "UPDATE alumnos SET nombre = ?, email = ?, codigo = ?, password = ? WHERE id = ?"
            : "UPDATE alumnos SET nombre = ?, email = ?, codigo = ? WHERE id = ?";
            
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, alumno.getNombre());
            pstmt.setString(2, alumno.getEmail());
            pstmt.setString(3, alumno.getCodigo());
            
            if (cambiarPass) {
                pstmt.setString(4, alumno.getPassword());
                pstmt.setInt(5, alumno.getId());
            } else {
                pstmt.setInt(4, alumno.getId());
            }
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar alumno: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminarAlumno(int id) {
        String sql = "DELETE FROM alumnos WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}