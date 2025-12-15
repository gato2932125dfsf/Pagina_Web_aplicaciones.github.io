package com.gestortes.dao;

import com.gestortes.conexion.ConexionDB;
import com.gestortes.modelo.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    public List<Admin> listarAdmins() {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT id, nombre, email FROM administradores";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setNombre(rs.getString("nombre"));
                admin.setEmail(rs.getString("email"));
                admins.add(admin);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar admins: " + e.getMessage());
        }
        return admins;
    }
    
    public Admin buscarPorId(int id) {
        String sql = "SELECT id, nombre, email FROM administradores WHERE id = ?";
        Admin admin = null;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    admin = new Admin();
                    admin.setId(rs.getInt("id"));
                    admin.setNombre(rs.getString("nombre"));
                    admin.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar admin: " + e.getMessage());
        }
        return admin;
    }

    public boolean crearAdmin(Admin admin) {
        String sql = "INSERT INTO administradores (nombre, email, password) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, admin.getNombre());
            pstmt.setString(2, admin.getEmail());
            pstmt.setString(3, admin.getPassword());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear admin: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizarAdmin(Admin admin) {
        String sql = "UPDATE administradores SET nombre = ?, email = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, admin.getNombre());
            pstmt.setString(2, admin.getEmail());
            pstmt.setInt(3, admin.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar admin: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminarAdmin(int id) {
        String sql = "DELETE FROM administradores WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar admin: " + e.getMessage());
            return false;
        }
    }
}