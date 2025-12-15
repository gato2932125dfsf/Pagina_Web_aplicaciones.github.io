package com.gestortes.servlets;

import com.gestortes.dao.NotificacionDAO;
import com.gestortes.dao.UsuarioDAO;
import com.gestortes.modelo.Usuario;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        // --- 1. MANEJO DE RECUPERACIÓN DE CONTRASEÑA ---
        if ("solicitarRecuperacion".equals(accion)) {
            String datoUsuario = request.getParameter("recuperacionInput");
            if(datoUsuario == null || datoUsuario.trim().isEmpty()) {
                response.sendRedirect("login.jsp?error=Debe ingresar un dato válido.");
                return;
            }
            
            // Crear notificación tipo ALERTA para el Admin (usuario_id = null)
            String msg = "El usuario con identificación '" + datoUsuario + "' solicitó recuperar su contraseña. Por favor verifique su identidad y actualice sus credenciales.";
            boolean ok = new NotificacionDAO().crearNotificacion(msg, "alerta", null);
            
            if(ok) {
                String msgExito = URLEncoder.encode("Solicitud enviada. El administrador se pondrá en contacto.", StandardCharsets.UTF_8.toString());
                response.sendRedirect("login.jsp?msg=" + msgExito);
            } else {
                response.sendRedirect("login.jsp?error=Error al enviar solicitud.");
            }
            return;
        }

        // --- 2. LOGIN NORMAL ---
        String loginId = request.getParameter("loginId");
        String loginPass = request.getParameter("loginPassword");
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.validarUsuario(loginId, loginPass);

        if (usuario != null) {
            // Crear sesión
            HttpSession session = request.getSession(true);
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("nombre", usuario.getNombre());
            session.setAttribute("rol", usuario.getRol());
            
            // Redirigir según rol
            switch (usuario.getRol()) {
                case "admin": response.sendRedirect("AdminController"); break;
                case "docente": response.sendRedirect("DocenteController"); break;
                case "estudiante": response.sendRedirect("AlumnoController"); break;
                default: response.sendRedirect("login.jsp");
            }
        } else {
            // Login fallido
            request.setAttribute("error", "Credenciales incorrectas o usuario no encontrado.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Redirigir GETs al login.jsp para evitar página en blanco
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}