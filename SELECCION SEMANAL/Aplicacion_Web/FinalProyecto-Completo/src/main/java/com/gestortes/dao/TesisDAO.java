package com.gestortes.dao;

import com.gestortes.conexion.ConexionDB;
import com.gestortes.modelo.HistorialRevision;
import com.gestortes.modelo.Tesis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar las operaciones CRUD de Tesis, archivos, revisiones y rúbricas.
 */
public class TesisDAO {

    /**
     * Obtiene una lista de todas las tesis con los nombres (JOIN) y el archivo actual.
     * Usado por el Admin.
     */
    public List<Tesis> listarTesisView() {
        List<Tesis> tesisList = new ArrayList<>();
        String sql = "SELECT t.id, t.titulo, t.estado, t.archivo_actual_url, " +
                     "a.nombre AS nombre_alumno, d.nombre AS nombre_docente, " +
                     "t.alumno_id, t.docente_id " +
                     "FROM tesis t " +
                     "JOIN alumnos a ON t.alumno_id = a.id " +
                     "JOIN docentes d ON t.docente_id = d.id";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Tesis tesis = new Tesis();
                tesis.setId(rs.getInt("id"));
                tesis.setTitulo(rs.getString("titulo"));
                tesis.setEstado(rs.getString("estado"));
                tesis.setArchivoActualUrl(rs.getString("archivo_actual_url"));
                tesis.setNombreAlumno(rs.getString("nombre_alumno"));
                tesis.setNombreDocente(rs.getString("nombre_docente"));
                tesis.setAlumnoId(rs.getInt("alumno_id"));
                tesis.setDocenteId(rs.getInt("docente_id"));
                tesisList.add(tesis);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar tesis: " + e.getMessage());
        }
        return tesisList;
    }

    /**
     * Busca una tesis por ID. Necesario para recuperar archivo actual, fechas y datos para Actas.
     */
    public Tesis buscarPorId(int id) {
        Tesis tesis = null;
        String sql = "SELECT t.id, t.titulo, t.estado, t.fecha_actualizacion, t.fecha_limite, t.archivo_actual_url, " +
                     "a.nombre as nombre_alumno, d.nombre as nombre_docente, " + 
                     "t.alumno_id, t.docente_id " +
                     "FROM tesis t " +
                     "JOIN alumnos a ON t.alumno_id = a.id " + 
                     "JOIN docentes d ON t.docente_id = d.id " + 
                     "WHERE t.id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tesis = new Tesis();
                    tesis.setId(rs.getInt("id"));
                    tesis.setTitulo(rs.getString("titulo"));
                    tesis.setEstado(rs.getString("estado"));
                    tesis.setArchivoActualUrl(rs.getString("archivo_actual_url"));
                    tesis.setNombreAlumno(rs.getString("nombre_alumno"));
                    tesis.setNombreDocente(rs.getString("nombre_docente"));
                    tesis.setFechaUltimaModificacion(rs.getTimestamp("fecha_actualizacion"));
                    tesis.setFechaLimite(rs.getDate("fecha_limite"));
                    tesis.setAlumnoId(rs.getInt("alumno_id"));
                    tesis.setDocenteId(rs.getInt("docente_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar tesis por ID: " + e.getMessage());
        }
        return tesis;
    }

    /**
     * Inserta una nueva tesis (Admin).
     */
    public boolean crearTesis(Tesis tesis) {
        String sql = "INSERT INTO tesis (titulo, alumno_id, docente_id, estado, archivo_actual_url, fecha_asignacion) VALUES (?, ?, ?, ?, ?, NOW())";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tesis.getTitulo());
            pstmt.setInt(2, tesis.getAlumnoId());
            pstmt.setInt(3, tesis.getDocenteId());
            pstmt.setString(4, tesis.getEstado());
            pstmt.setString(5, tesis.getArchivoActualUrl());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear tesis: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Actualiza datos de la tesis (Admin), incluyendo el archivo si se subió uno nuevo.
     */
    public boolean actualizarTesis(Tesis tesis) {
        String sql = "UPDATE tesis SET titulo = ?, alumno_id = ?, docente_id = ?, estado = ?, archivo_actual_url = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tesis.getTitulo());
            pstmt.setInt(2, tesis.getAlumnoId());
            pstmt.setInt(3, tesis.getDocenteId());
            pstmt.setString(4, tesis.getEstado());
            pstmt.setString(5, tesis.getArchivoActualUrl());
            pstmt.setInt(6, tesis.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar tesis: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina una tesis (Admin).
     */
    public boolean eliminarTesis(int id) {
        String sql = "DELETE FROM tesis WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar tesis: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista las tesis de un docente (incluye fecha_actualizacion para semáforo y fecha_limite).
     */
    public List<Tesis> listarTesisViewPorDocente(int docenteId) {
        List<Tesis> tesisList = new ArrayList<>();
        String sql = "SELECT t.id, t.titulo, t.estado, t.archivo_actual_url, t.fecha_actualizacion, t.fecha_limite, " +
                     "a.nombre AS nombre_alumno, d.nombre AS nombre_docente, " +
                     "t.alumno_id, t.docente_id " +
                     "FROM tesis t " +
                     "JOIN alumnos a ON t.alumno_id = a.id " +
                     "JOIN docentes d ON t.docente_id = d.id " +
                     "WHERE t.docente_id = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, docenteId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Tesis tesis = new Tesis();
                    tesis.setId(rs.getInt("id"));
                    tesis.setTitulo(rs.getString("titulo"));
                    tesis.setEstado(rs.getString("estado"));
                    tesis.setArchivoActualUrl(rs.getString("archivo_actual_url"));
                    tesis.setFechaUltimaModificacion(rs.getTimestamp("fecha_actualizacion")); // Para semáforo
                    tesis.setFechaLimite(rs.getDate("fecha_limite")); // Para alertas de plazo
                    tesis.setNombreAlumno(rs.getString("nombre_alumno"));
                    tesis.setNombreDocente(rs.getString("nombre_docente"));
                    tesis.setAlumnoId(rs.getInt("alumno_id"));
                    tesis.setDocenteId(rs.getInt("docente_id"));
                    tesisList.add(tesis);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar tesis por docente: " + e.getMessage());
        }
        return tesisList;
    }

    /**
     * Busca la tesis asignada a un alumno específico.
     */
    public Tesis buscarTesisViewPorAlumno(int alumnoId) {
        Tesis tesis = null;
        String sql = "SELECT t.id, t.titulo, t.estado, t.archivo_actual_url, " +
                     "a.nombre AS nombre_alumno, d.nombre AS nombre_docente, " +
                     "t.alumno_id, t.docente_id " +
                     "FROM tesis t " +
                     "JOIN alumnos a ON t.alumno_id = a.id " +
                     "JOIN docentes d ON t.docente_id = d.id " +
                     "WHERE t.alumno_id = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, alumnoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tesis = new Tesis();
                    tesis.setId(rs.getInt("id"));
                    tesis.setTitulo(rs.getString("titulo"));
                    tesis.setEstado(rs.getString("estado"));
                    tesis.setArchivoActualUrl(rs.getString("archivo_actual_url"));
                    tesis.setNombreAlumno(rs.getString("nombre_alumno"));
                    tesis.setNombreDocente(rs.getString("nombre_docente"));
                    tesis.setAlumnoId(rs.getInt("alumno_id"));
                    tesis.setDocenteId(rs.getInt("docente_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar tesis por alumno: " + e.getMessage());
        }
        return tesis;
    }

    /**
     * Lista el historial completo de revisiones (Con Nota y Rubrica detallada).
     */
    public List<HistorialRevision> listarHistorialPorTesis(int tesisId) {
        List<HistorialRevision> historial = new ArrayList<>();
        String sql = "SELECT * FROM historial_revisiones WHERE tesis_id = ? ORDER BY numero_version DESC";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tesisId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    HistorialRevision h = new HistorialRevision();
                    h.setId(rs.getInt("id"));
                    h.setNumeroVersion(rs.getInt("numero_version"));
                    h.setEstadoVersion(rs.getString("estado_version"));
                    h.setComentariosDocente(rs.getString("comentarios_docente"));
                    h.setArchivoUrl(rs.getString("archivo_url"));
                    h.setFechaSubida(rs.getTimestamp("fecha_subida"));
                    
                    // Recuperar datos de rúbrica si existen
                    try {
                        h.setPuntajeTotal(rs.getDouble("puntaje_total"));
                        h.setDetalleRubrica(rs.getString("detalle_rubrica"));
                    } catch (SQLException e) {
                        // Columnas pueden no existir si no se corrió el script SQL completo, se ignora
                    }
                    
                    historial.add(h);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar historial: " + e.getMessage());
        }
        return historial;
    }

    /**
     * Guarda la revisión de un docente CON RÚBRICA y FECHA LÍMITE.
     * Usa TRANSACCIÓN para asegurar que se actualice la tesis y se cree el historial.
     */
    public boolean guardarRevisionConRubrica(int tesisId, String nuevoEstado, String comentarios, double puntajeTotal, String detalleRubrica, Date nuevaFechaLimite) {
        
        Connection conn = null;
        PreparedStatement pstmtUpdate = null;
        PreparedStatement pstmtInsert = null;
        
        // 1. SQL Update Tesis (Estado y Fecha Límite)
        String sqlUpdate = "UPDATE tesis SET estado = ?, fecha_limite = ? WHERE id = ?";
        
        // 2. SQL Insert Historial (Con puntaje y detalle rúbrica)
        String sqlInsert = "INSERT INTO historial_revisiones " +
                     "(tesis_id, numero_version, estado_version, comentarios_docente, archivo_url, fecha_subida, puntaje_total, detalle_rubrica) " +
                     "VALUES (?, " +
                     "(SELECT IFNULL(MAX(v.num), 0) + 1 FROM (SELECT numero_version as num FROM historial_revisiones WHERE tesis_id = ?) as v), " +
                     "?, ?, ?, NOW(), ?, ?)";
        
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false); // Iniciar Transacción
            
            // Ejecutar Update
            pstmtUpdate = conn.prepareStatement(sqlUpdate);
            pstmtUpdate.setString(1, nuevoEstado);
            pstmtUpdate.setDate(2, nuevaFechaLimite);
            pstmtUpdate.setInt(3, tesisId);
            pstmtUpdate.executeUpdate();
            
            // Ejecutar Insert
            pstmtInsert = conn.prepareStatement(sqlInsert);
            pstmtInsert.setInt(1, tesisId);
            pstmtInsert.setInt(2, tesisId); // Subquery tesis_id
            pstmtInsert.setString(3, nuevoEstado);
            pstmtInsert.setString(4, comentarios);
            pstmtInsert.setString(5, null); // URL es null para revisión docente
            pstmtInsert.setDouble(6, puntajeTotal);
            pstmtInsert.setString(7, detalleRubrica);
            pstmtInsert.executeUpdate();
            
            conn.commit(); // Confirmar Transacción
            return true;
            
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {} // Rollback si falla
            System.err.println("Error transacción revisión: " + e.getMessage());
            return false;
        } finally {
            // Cerrar recursos manualmente
            if (pstmtUpdate != null) try { pstmtUpdate.close(); } catch (SQLException e) {}
            if (pstmtInsert != null) try { pstmtInsert.close(); } catch (SQLException e) {}
            ConexionDB.close(conn);
        }
    }
    
    /**
     * Método legacy para compatibilidad (sin rúbrica/fecha). Llama al completo con defaults.
     */
    public boolean guardarRevision(int tesisId, String nuevoEstado, String comentarios) {
        return guardarRevisionConRubrica(tesisId, nuevoEstado, comentarios, 0.0, null, null);
    }
    
    /**
     * Crea una nueva entrada en el historial (Usado por Alumno al subir archivo).
     */
    public boolean crearNuevaVersion(int tesisId, String estado, String comentarios, String archivoUrl) {
        String sql = "INSERT INTO historial_revisiones " +
                     "(tesis_id, numero_version, estado_version, comentarios_docente, archivo_url, fecha_subida) " +
                     "VALUES (?, " +
                     "(SELECT IFNULL(MAX(v.num), 0) + 1 FROM (SELECT numero_version as num FROM historial_revisiones WHERE tesis_id = ?) as v), " +
                     "?, ?, ?, NOW())";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tesisId);
            pstmt.setInt(2, tesisId);
            pstmt.setString(3, estado);
            pstmt.setString(4, comentarios);
            pstmt.setString(5, archivoUrl);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear nueva versión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza solo el estado en la tabla tesis (Método auxiliar simple).
     */
    public boolean actualizarEstadoTesis(int tesisId, String estado) {
        String sql = "UPDATE tesis SET estado = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, estado);
            pstmt.setInt(2, tesisId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado de tesis: " + e.getMessage());
            return false;
        }
    }
}