package com.gestortes.servlets;

import com.gestortes.dao.NotificacionDAO;
import com.gestortes.dao.TesisDAO;
import com.gestortes.modelo.HistorialRevision;
import com.gestortes.modelo.Notificacion;
import com.gestortes.modelo.Tesis;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

@WebServlet(name = "AlumnoController", urlPatterns = {"/AlumnoController"})
@MultipartConfig(fileSizeThreshold = 1024*1024*2, maxFileSize = 1024*1024*50, maxRequestSize = 1024*1024*100)
public class AlumnoController extends HttpServlet {

    private static final String UPLOAD_DIR = "C:\\arred";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String accion = request.getParameter("accion");
        boolean isAjax = "verDetalleRubrica".equals(accion);

        if (session == null || session.getAttribute("usuarioId") == null || !session.getAttribute("rol").equals("estudiante")) {
            if (isAjax) response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            else response.sendRedirect("login.jsp?error=Acceso denegado.");
            return; 
        }

        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar": cargarPanel(request, response); break;
            case "marcarLeidas": marcarNotificacionesLeidas(request, response); break;
            case "subirVersion": subirNuevaVersion(request, response); break;
            case "verDetalleRubrica": verDetalleRubricaAjax(request, response); break;
            default: cargarPanel(request, response); break;
        }
    }

    private void cargarPanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int alumnoId = (int) session.getAttribute("usuarioId");
        
        TesisDAO tesisDAO = new TesisDAO();
        Tesis tesis = tesisDAO.buscarTesisViewPorAlumno(alumnoId);
        
        request.setAttribute("tesis", tesis);
        if (tesis != null) {
            List<HistorialRevision> historial = tesisDAO.listarHistorialPorTesis(tesis.getId());
            request.setAttribute("historial", historial);
        }
        
        // CARGAR NOTIFICACIONES
        List<Notificacion> notificaciones = new NotificacionDAO().listarNoLeidasUsuario(alumnoId);
        request.setAttribute("listaNotificaciones", notificaciones);
        request.setAttribute("countNotificaciones", notificaciones.size());
        
        request.getRequestDispatcher("alumno.jsp").forward(request, response);
    }
    
    private void marcarNotificacionesLeidas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int alumnoId = (int) session.getAttribute("usuarioId");
        new NotificacionDAO().marcarTodasLeidasUsuario(alumnoId);
        response.sendRedirect("AlumnoController");
    }

    private void subirNuevaVersion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String nombreAlumno = (String) session.getAttribute("nombre");
        int tesisId = Integer.parseInt(request.getParameter("tesisId"));
        Part filePart = request.getPart("archivoTesis");
        
        String nombreArchivo = null;
        if (filePart != null && filePart.getSize() > 0) {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            String rawName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            nombreArchivo = System.currentTimeMillis() + "_" + rawName;
            filePart.write(UPLOAD_DIR + File.separator + nombreArchivo);
        }
        
        TesisDAO tesisDAO = new TesisDAO();
        tesisDAO.actualizarEstadoTesis(tesisId, "En Revisión");
        tesisDAO.crearNuevaVersion(tesisId, "En Revisión", null, nombreArchivo);
        
        // NOTIFICAR DOCENTE
        Tesis tesisInfo = tesisDAO.buscarPorId(tesisId);
        if (tesisInfo != null) {
            String msgDocente = "El alumno " + nombreAlumno + " ha subido una nueva versión de su tesis.";
            new NotificacionDAO().crearNotificacion(msgDocente, "alerta", tesisInfo.getDocenteId());
        }
        
        response.sendRedirect("AlumnoController?msg=" + URLEncoder.encode("Archivo enviado. Docente notificado.", StandardCharsets.UTF_8.toString()));
    }

    // AJAX Ver Detalle (Igual al del docente pero solo lectura)
    private void verDetalleRubricaAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Reutilizamos la misma lógica visual que ya probamos
        int tesisId = Integer.parseInt(request.getParameter("tesisId"));
        TesisDAO dao = new TesisDAO();
        List<HistorialRevision> historial = dao.listarHistorialPorTesis(tesisId);
        HistorialRevision ultima = null;
        for (HistorialRevision h : historial) { if (h.getPuntajeTotal() > 0) { ultima = h; break; } }
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (ultima == null) { out.println("<div class='p-4 text-center text-muted'>Aún no hay calificación.</div>"); return; }
        
        double puntaje = ultima.getPuntajeTotal();
        int pct = (int)((puntaje/38.0)*100);
        String color = puntaje >= 25 ? "#10b981" : (puntaje >= 13 ? "#f59e0b" : "#ef4444");
        
        out.println("<div class='p-4 text-center'>");
        out.println("<h1 style='color:"+color+"; font-size: 4rem; font-weight:bold; margin-bottom:0;'>" + puntaje + "</h1>");
        out.println("<p class='text-muted text-uppercase small'>Puntos de 38</p>");
        out.println("<div class='progress mt-3 mb-4' style='height: 8px;'><div class='progress-bar' style='background-color:"+color+"; width:"+pct+"%'></div></div>");
        out.println("<div class='alert alert-light text-start border'><strong>Comentario Docente:</strong> "+ (ultima.getComentariosDocente()!=null ? ultima.getComentariosDocente() : "Sin comentarios") +"</div>");
        out.println("</div>");
    }
    
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { processRequest(req, resp); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { processRequest(req, resp); }
}