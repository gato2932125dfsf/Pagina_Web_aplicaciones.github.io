package com.gestortes.servlets;

import com.gestortes.dao.NotificacionDAO;
import com.gestortes.dao.TesisDAO;
import com.gestortes.modelo.HistorialRevision;
import com.gestortes.modelo.Notificacion;
import com.gestortes.modelo.Tesis;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "DocenteController", urlPatterns = {"/DocenteController"})
public class DocenteController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String accion = request.getParameter("accion");
        
        boolean isAjax = "verHistorial".equals(accion) || "verActa".equals(accion) || "verDetalleRubrica".equals(accion);
        
        if (session == null || session.getAttribute("usuarioId") == null || !session.getAttribute("rol").equals("docente")) {
            if (isAjax) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                response.sendRedirect("login.jsp?error=Acceso denegado.");
            }
            return; 
        }

        if (accion == null) accion = "listar";

        switch (accion) {
            case "listar": cargarPanel(request, response); break;
            case "marcarLeidas": marcarNotificacionesLeidas(request, response); break;
            case "guardarRevision": guardarRevision(request, response); break;
            case "verHistorial": verHistorialAjax(request, response); break;
            case "verActa": generarActaAjax(request, response); break;
            case "verDetalleRubrica": verDetalleRubricaAjax(request, response); break;
            default: cargarPanel(request, response); break;
        }
    }

    private void cargarPanel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int docenteId = (int) session.getAttribute("usuarioId");
        
        // Cargar Tesis
        List<Tesis> listaTesis = new TesisDAO().listarTesisViewPorDocente(docenteId);
        request.setAttribute("listaTesis", listaTesis);
        
        // Cargar Notificaciones
        List<Notificacion> notificaciones = new NotificacionDAO().listarNoLeidasUsuario(docenteId);
        request.setAttribute("listaNotificaciones", notificaciones);
        request.setAttribute("countNotificaciones", notificaciones.size());
        
        request.getRequestDispatcher("docente.jsp").forward(request, response);
    }
    
    private void marcarNotificacionesLeidas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        int docenteId = (int) session.getAttribute("usuarioId");
        new NotificacionDAO().marcarTodasLeidasUsuario(docenteId);
        response.sendRedirect("DocenteController");
    }

    // --- EVALUACIÓN Y NOTIFICACIÓN ALUMNO ---
    private void guardarRevision(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String nombreDocente = (String) session.getAttribute("nombre");
        int tesisId = Integer.parseInt(request.getParameter("tesisId"));
        String comentarios = request.getParameter("comentarios");
        
        String fechaStr = request.getParameter("fechaLimite");
        Date fechaLimite = (fechaStr != null && !fechaStr.isEmpty()) ? Date.valueOf(fechaStr) : null;

        double puntajeTotal = 0;
        List<String> detalles = new ArrayList<>();
        for (int i = 1; i <= 38; i++) {
            String valStr = request.getParameter("criterio_" + i);
            double val = (valStr != null && !valStr.isEmpty()) ? Double.parseDouble(valStr) : 0.0;
            puntajeTotal += val;
            detalles.add(String.valueOf(val));
        }
        String detalleRubrica = String.join(",", detalles);
        
        String nuevoEstado = (puntajeTotal >= 25) ? "Aprobado" : (puntajeTotal >= 13) ? "Observado" : "Rechazado";
        
        TesisDAO tesisDAO = new TesisDAO();
        tesisDAO.guardarRevisionConRubrica(tesisId, nuevoEstado, comentarios, puntajeTotal, detalleRubrica, fechaLimite);
        
        // --- NOTIFICAR ALUMNO ---
        Tesis t = tesisDAO.buscarPorId(tesisId);
        if (t != null) {
            String msgAlumno = "Tu tesis ha sido evaluada por " + nombreDocente + ". Nota: " + puntajeTotal + " (" + nuevoEstado + ")";
            new NotificacionDAO().crearNotificacion(msgAlumno, "info", t.getAlumnoId());
        }
        
        String msg = "Evaluación guardada. Nota: " + puntajeTotal + " (" + nuevoEstado + ")";
        response.sendRedirect("DocenteController?msg=" + URLEncoder.encode(msg, StandardCharsets.UTF_8.toString()));
    }

    // --- AJAX: DETALLE RÚBRICA ---
    private void verDetalleRubricaAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int tesisId = Integer.parseInt(request.getParameter("tesisId"));
        TesisDAO dao = new TesisDAO();
        Tesis tesis = dao.buscarPorId(tesisId);
        List<HistorialRevision> historial = dao.listarHistorialPorTesis(tesisId);
        HistorialRevision ultima = (historial != null && !historial.isEmpty()) ? historial.get(0) : null;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (ultima == null || ultima.getPuntajeTotal() == 0) { out.println("<div class='text-center py-5 text-muted'>Sin evaluación calificada.</div>"); return; }
        double puntaje = ultima.getPuntajeTotal();
        int porcentaje = (int) ((puntaje / 38.0) * 100);
        String color = (puntaje >= 25) ? "#10b981" : (puntaje >= 13) ? "#f59e0b" : "#ef4444";
        String colorClase = (puntaje >= 25) ? "success" : (puntaje >= 13) ? "warning" : "danger";
        out.println("<div class='container-fluid p-3'><div class='row align-items-center'><div class='col-md-4 text-center'><div class='position-relative d-inline-block' style='width:140px; height:140px;'><svg viewBox='0 0 36 36' class='circular-chart' style='display:block; max-width:100%; max-height:100%;'><path class='circle-bg' d='M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831' fill='none' stroke='#eee' stroke-width='3' /><path class='circle' stroke-dasharray='" + porcentaje + ", 100' d='M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831' fill='none' stroke='" + color + "' stroke-width='3' stroke-linecap='round' /></svg><div class='position-absolute top-50 start-50 translate-middle text-center'><span class='d-block h2 fw-bold mb-0' style='color:" + color + "'>" + puntaje + "</span><span class='small text-muted'>de 38</span></div></div></div><div class='col-md-8'><h5 class='fw-bold text-dark mb-1'>" + tesis.getTitulo() + "</h5><p class='text-muted small mb-2'><i class='bi bi-person'></i> " + tesis.getNombreAlumno() + "</p><span class='badge bg-" + colorClase + " mb-3'>" + ultima.getEstadoVersion().toUpperCase() + "</span><div class='p-2 bg-light border rounded small'><strong>Feedback:</strong> " + (ultima.getComentariosDocente() != null ? ultima.getComentariosDocente() : "Ninguno") + "</div></div></div></div>");
        
        String[] puntos = (ultima.getDetalleRubrica() != null) ? ultima.getDetalleRubrica().split(",") : new String[0];
        if (puntos.length > 0) {
            out.println("<hr><div class='table-responsive' style='max-height:250px;'><table class='table table-sm table-bordered mb-0' style='font-size:0.8rem;'><thead class='table-light'><tr><th>Item</th><th class='text-center'>Pts</th></tr></thead><tbody>");
            for (int i = 0; i < puntos.length; i++) {
                double val = Double.parseDouble(puntos[i]);
                String cls = (val == 1.0) ? "text-success fw-bold" : (val == 0.5 ? "text-warning" : "text-danger");
                out.println("<tr><td>Criterio " + (i + 1) + "</td><td class='text-center " + cls + "'>" + val + "</td></tr>");
            }
            out.println("</tbody></table></div>");
        }
        out.println("</div>");
    }

    // --- AJAX: ACTA IMPRIMIBLE (FORMAL) ---
    private void generarActaAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int tesisId = Integer.parseInt(request.getParameter("tesisId"));
        TesisDAO dao = new TesisDAO();
        Tesis tesis = dao.buscarPorId(tesisId);
        List<HistorialRevision> historial = dao.listarHistorialPorTesis(tesisId);
        HistorialRevision ultima = (historial != null && !historial.isEmpty()) ? historial.get(0) : null;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (ultima == null || ultima.getPuntajeTotal() == 0) { out.println("<div class='alert alert-warning'>Sin evaluación.</div>"); return; }
        
        // HTML ACTA COMPLETO
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
        String fecha = sdf.format(new java.util.Date());
        out.println("<div class='p-5' style='font-family: \"Times New Roman\", serif; max-width:800px; margin:0 auto; color:black;'>");
        out.println("<div class='text-center mb-5'><h3 class='text-uppercase fw-bold'>Universidad Nacional</h3><h5>Facultad de Ingeniería</h5><hr class='border-dark my-4'><h2 class='text-uppercase fw-bold text-decoration-underline'>Acta de Evaluación</h2></div>");
        out.println("<div class='my-5 lh-lg text-justify'><p>El docente revisor certifica la evaluación del proyecto: <strong>\""+tesis.getTitulo()+"\"</strong>.</p>");
        out.println("<p>Presentado por: <strong>"+tesis.getNombreAlumno()+"</strong>.</p>");
        out.println("<div class='row justify-content-center my-5'><div class='col-8 border border-dark p-4 text-center bg-light'><span class='d-block small text-uppercase text-muted'>Nota Final</span><span class='display-4 fw-bold'>"+ultima.getPuntajeTotal()+" / 38</span><hr><span class='h3 fw-bold'>"+ultima.getEstadoVersion().toUpperCase()+"</span></div></div>");
        out.println("<p><strong>Observaciones:</strong> <em>"+(ultima.getComentariosDocente()!=null?ultima.getComentariosDocente():"Ninguna")+"</em></p>");
        out.println("<p class='text-end mt-5'>Huancayo, "+fecha+"</p></div>");
        out.println("<div class='text-center mt-5 pt-5'><div style='border-top:1px solid black; width:300px; display:inline-block; padding-top:10px;'><strong>"+tesis.getNombreDocente()+"</strong><br>Docente Revisor</div></div>");
        out.println("<div class='text-center mt-5 no-print'><button onclick='window.print()' class='btn btn-dark'><i class='bi bi-printer'></i> Imprimir</button></div></div>");
    }

    private void verHistorialAjax(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int tesisId = Integer.parseInt(request.getParameter("tesisId"));
        List<HistorialRevision> historial = new TesisDAO().listarHistorialPorTesis(tesisId);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        out.println("<ul class='list-group list-group-flush'>");
        for(HistorialRevision h : historial) {
            out.println("<li class='list-group-item'><div><span class='fw-bold'>Versión "+h.getNumeroVersion()+"</span> - "+h.getEstadoVersion()+" <small class='text-muted ms-2'>"+sdf.format(h.getFechaSubida())+"</small></div>");
            if(h.getPuntajeTotal()>0) out.println("<div class='small text-success fw-bold'>Nota: "+h.getPuntajeTotal()+"</div>");
            if(h.getComentariosDocente()!=null) out.println("<div class='small text-muted fst-italic'>"+h.getComentariosDocente()+"</div>");
            if(h.getArchivoUrl()!=null) out.println("<a href='DescargaServlet?archivo="+h.getArchivoUrl()+"' target='_blank' class='btn btn-sm btn-link p-0'>Ver Archivo</a>");
            out.println("</li>");
        }
        out.println("</ul>");
    }
    
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { processRequest(req, resp); }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { processRequest(req, resp); }
}