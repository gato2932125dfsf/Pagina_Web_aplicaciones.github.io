package com.gestortes.dao;

import com.gestortes.conexion.ConexionDB;
import com.gestortes.modelo.Docente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocenteDAO {

    public List<Docente> listarDocentes() {
        List<Docente> docentes = new ArrayList<>();
        String sql = "SELECT id, nombre, email, dni FROM docentes";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Docente docente = new Docente();
                docente.setId(rs.getInt("id"));
                docente.setNombre(rs.getString("nombre"));
                docente.setEmail(rs.getString("email"));
                docente.setDni(rs.getString("dni"));
                docentes.add(docente);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return docentes;
    }
    
    public Docente buscarPorId(int id) { /* ... igual que antes ... */ return null; }

    public boolean crearDocente(Docente docente) {
        String sql = "INSERT INTO docentes (nombre, email, dni, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, docente.getNombre());
            pstmt.setString(2, docente.getEmail());
            pstmt.setString(3, docente.getDni());
            pstmt.setString(4, docente.getPassword());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
    
    // --- MÉTODO ACTUALIZADO CON PASSWORD OPCIONAL ---
    public boolean actualizarDocente(Docente docente) {
        boolean cambiarPass = docente.getPassword() != null && !docente.getPassword().trim().isEmpty();
        
        String sql = cambiarPass 
            ? "UPDATE docentes SET nombre = ?, email = ?, dni = ?, password = ? WHERE id = ?"
            : "UPDATE docentes SET nombre = ?, email = ?, dni = ? WHERE id = ?";
            
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, docente.getNombre());
            pstmt.setString(2, docente.getEmail());
            pstmt.setString(3, docente.getDni());
            
            if (cambiarPass) {
                pstmt.setString(4, docente.getPassword());
                pstmt.setInt(5, docente.getId());
            } else {
                pstmt.setInt(4, docente.getId());
            }
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar docente: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminarDocente(int id) {
        String sql = "DELETE FROM docentes WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}