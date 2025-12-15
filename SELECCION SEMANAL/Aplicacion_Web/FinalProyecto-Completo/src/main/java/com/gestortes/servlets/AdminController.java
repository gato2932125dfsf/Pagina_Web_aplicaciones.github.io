package com.gestortes.servlets;

import com.gestortes.dao.*;
import com.gestortes.modelo.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
    maxFileSize = 1024 * 1024 * 50,       // 50MB
    maxRequestSize = 1024 * 1024 * 100    // 100MB
)
public class AdminController extends HttpServlet {

    private static final String UPLOAD_DIR = "C:\\arred";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null || !session.getAttribute("rol").equals("admin")) {
            response.sendRedirect("login.jsp?error=Acceso denegado.");
            return; 
        }

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar": cargarListas(request, response); break;
            case "marcarLeidas": marcarNotificacionesLeidas(request, response); break;
            case "eliminarDocente": eliminarDocente(request, response); break;
            case "eliminarAlumno": eliminarAlumno(request, response); break;
            case "eliminarAdmin": eliminarAdmin(request, response); break;
            case "eliminarTesis": eliminarTesis(request, response); break;
            default: cargarListas(request, response); break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null || !session.getAttribute("rol").equals("admin")) {
            response.sendRedirect("login.jsp?error=Acceso denegado.");
            return; 
        }
        
        String accion = request.getParameter("accion");
        if (accion == null) { response.sendRedirect("AdminController"); return; }

        switch (accion) {
            // CREACIÓN
            case "crearDocente": crearDocente(request, response); break;
            case "crearAlumno": crearAlumno(request, response); break;
            case "crearAdmin": crearAdmin(request, response); break;
            case "crearTesis": crearTesis(request, response); break;
            
            // ACTUALIZACIÓN
            case "actualizarDocente": actualizarDocente(request, response); break;
            case "actualizarAlumno": actualizarAlumno(request, response); break;
            case "actualizarAdmin": actualizarAdmin(request, response); break;
            case "actualizarTesis": actualizarTesis(request, response); break;
            
            default: response.sendRedirect("AdminController"); break;
        }
    }
    
    // --- MÉTODOS DE CARGA DE DATOS ---

    private void cargarListas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tesis> listaTesis = new TesisDAO().listarTesisView();
        List<Docente> listaDocentes = new DocenteDAO().listarDocentes();
        List<Alumno> listaAlumnos = new AlumnoDAO().listarAlumnos();
        List<Admin> listaAdmins = new AdminDAO().listarAdmins();
        
        // Notificaciones (Solo las dirigidas al Admin, es decir usuario_id IS NULL)
        List<Notificacion> notificaciones = new NotificacionDAO().listarNoLeidasAdmin();
        request.setAttribute("listaNotificaciones", notificaciones);
        request.setAttribute("countNotificaciones", notificaciones.size());

        // Estadísticas para Dashboard
        double total = listaTesis.size();
        long countAprobados = listaTesis.stream().filter(t -> "Aprobado".equalsIgnoreCase(t.getEstado())).count();
        long countObservados = listaTesis.stream().filter(t -> "Observado".equalsIgnoreCase(t.getEstado())).count();
        long countAsignados = listaTesis.stream().filter(t -> "Asignado".equalsIgnoreCase(t.getEstado())).count();
        
        int pctAprob = (total > 0) ? (int)((countAprobados / total) * 100) : 0;
        int pctObs = (total > 0) ? (int)((countObservados / total) * 100) : 0;

        request.setAttribute("statsTotalTesis", (int)total);
        request.setAttribute("statsAprobados", countAprobados);
        request.setAttribute("statsObservados", countObservados);
        request.setAttribute("statsAsignados", countAsignados);
        request.setAttribute("statsDocentes", listaDocentes.size());
        request.setAttribute("statsAlumnos", listaAlumnos.size());
        request.setAttribute("pctAprob", pctAprob);
        request.setAttribute("pctObs", pctObs);

        request.setAttribute("listaTesis", listaTesis);
        request.setAttribute("listaDocentes", listaDocentes);
        request.setAttribute("listaAlumnos", listaAlumnos);
        request.setAttribute("listaAdmins", listaAdmins);
        
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }
    
    private void marcarNotificacionesLeidas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new NotificacionDAO().marcarTodasLeidasAdmin();
        response.sendRedirect("AdminController");
    }

    // --- MÉTODOS DE CREACIÓN ---

    private void crearTesis(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String titulo = request.getParameter("tesisTitulo");
        int alumnoId = Integer.parseInt(request.getParameter("tesisAlumno"));
        int docenteId = Integer.parseInt(request.getParameter("tesisDocente"));
        
        String fileName = guardarArchivo(request.getPart("tesisArchivo"));

        Tesis tesis = new Tesis();
        tesis.setTitulo(titulo);
        tesis.setAlumnoId(alumnoId);
        tesis.setDocenteId(docenteId);
        tesis.setEstado("Asignado");
        tesis.setArchivoActualUrl(fileName);
        
        new TesisDAO().crearTesis(tesis);
        
        // Notificar al docente asignado
        String msg = "Se le ha asignado el proyecto: '" + titulo + "'.";
        new NotificacionDAO().crearNotificacion(msg, "sistema", docenteId);
        
        response.sendRedirect("AdminController?msg=Tesis creada y asignada.");
    }

    private void crearDocente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Docente d = new Docente();
        d.setNombre(request.getParameter("docenteNombre"));
        d.setEmail(request.getParameter("docenteEmail"));
        d.setDni(request.getParameter("docenteDNI"));
        d.setPassword(request.getParameter("docentePass"));
        new DocenteDAO().crearDocente(d);
        response.sendRedirect("AdminController?msg=Docente creado");
    }

    private void crearAlumno(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Alumno a = new Alumno();
        a.setNombre(request.getParameter("alumnoNombre"));
        a.setEmail(request.getParameter("alumnoEmail"));
        a.setCodigo(request.getParameter("alumnoCodigo"));
        a.setPassword(request.getParameter("alumnoPass"));
        new AlumnoDAO().crearAlumno(a);
        response.sendRedirect("AdminController?msg=Alumno creado");
    }

    private void crearAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Admin a = new Admin();
        a.setNombre(request.getParameter("adminNombre"));
        a.setEmail(request.getParameter("adminEmail"));
        a.setPassword(request.getParameter("adminPass"));
        new AdminDAO().crearAdmin(a);
        response.sendRedirect("AdminController?msg=Admin creado");
    }

    // --- MÉTODOS DE ACTUALIZACIÓN ---

    private void actualizarTesis(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        TesisDAO dao = new TesisDAO();
        int id = Integer.parseInt(request.getParameter("editarTesisId"));
        String titulo = request.getParameter("editarTesisTitulo");
        int alumnoId = Integer.parseInt(request.getParameter("editarTesisAlumno"));
        int docenteId = Integer.parseInt(request.getParameter("editarTesisDocente"));
        String estado = request.getParameter("editarTesisEstado");
        
        // Lógica para mantener archivo si no se sube uno nuevo
        Tesis tesisActual = dao.buscarPorId(id);
        String nuevoArchivo = guardarArchivo(request.getPart("editarTesisArchivo"));
        
        Tesis tesis = new Tesis();
        tesis.setId(id);
        tesis.setTitulo(titulo);
        tesis.setAlumnoId(alumnoId);
        tesis.setDocenteId(docenteId);
        tesis.setEstado(estado);
        
        if (nuevoArchivo != null) {
            tesis.setArchivoActualUrl(nuevoArchivo);
        } else if (tesisActual != null) {
            tesis.setArchivoActualUrl(tesisActual.getArchivoActualUrl());
        }
        
        dao.actualizarTesis(tesis);
        
        // Si cambió el docente, notificar al nuevo
        if (tesisActual != null && tesisActual.getDocenteId() != docenteId) {
             new NotificacionDAO().crearNotificacion("Se le ha reasignado el proyecto: '" + titulo + "'", "sistema", docenteId);
        }
        
        response.sendRedirect("AdminController?msg=Tesis actualizada");
    }

    private void actualizarDocente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Docente d = new Docente();
        d.setId(Integer.parseInt(request.getParameter("editarDocenteId")));
        d.setNombre(request.getParameter("editarDocenteNombre"));
        d.setEmail(request.getParameter("editarDocenteEmail"));
        d.setDni(request.getParameter("editarDocenteDNI"));
        // DAO ignora contraseña si viene vacía
        d.setPassword(request.getParameter("editarDocentePass")); 
        new DocenteDAO().actualizarDocente(d);
        response.sendRedirect("AdminController?msg=Docente actualizado");
    }

    private void actualizarAlumno(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Alumno a = new Alumno();
        a.setId(Integer.parseInt(request.getParameter("editarAlumnoId")));
        a.setNombre(request.getParameter("editarAlumnoNombre"));
        a.setEmail(request.getParameter("editarAlumnoEmail"));
        a.setCodigo(request.getParameter("editarAlumnoCodigo"));
        a.setPassword(request.getParameter("editarAlumnoPass"));
        new AlumnoDAO().actualizarAlumno(a);
        response.sendRedirect("AdminController?msg=Alumno actualizado");
    }

    private void actualizarAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Admin a = new Admin();
        a.setId(Integer.parseInt(request.getParameter("editarAdminId")));
        a.setNombre(request.getParameter("editarAdminNombre"));
        a.setEmail(request.getParameter("editarAdminEmail"));
        new AdminDAO().actualizarAdmin(a);
        response.sendRedirect("AdminController?msg=Admin actualizado");
    }

    // --- MÉTODOS DE ELIMINACIÓN ---

    private void eliminarDocente(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new DocenteDAO().eliminarDocente(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect("AdminController?msg=Docente eliminado");
    }
    private void eliminarAlumno(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new AlumnoDAO().eliminarAlumno(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect("AdminController?msg=Alumno eliminado");
    }
    private void eliminarAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new AdminDAO().eliminarAdmin(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect("AdminController?msg=Admin eliminado");
    }
    private void eliminarTesis(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new TesisDAO().eliminarTesis(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect("AdminController?msg=Tesis eliminada");
    }

    // --- UTILITARIO ---
    private String guardarArchivo(Part filePart) throws IOException {
        if (filePart != null && filePart.getSize() > 0) {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            String rawName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileName = System.currentTimeMillis() + "_" + rawName;
            filePart.write(UPLOAD_DIR + File.separator + fileName);
            return fileName;
        }
        return null;
    }
}