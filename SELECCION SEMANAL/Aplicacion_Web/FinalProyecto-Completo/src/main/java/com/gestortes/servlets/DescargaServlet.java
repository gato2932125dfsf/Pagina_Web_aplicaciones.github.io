package com.gestortes.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet para descargar archivos de forma segura desde C:\arred
 * Uso: DescargaServlet?archivo=nombre_archivo.pdf
 */
@WebServlet(name = "DescargaServlet", urlPatterns = {"/DescargaServlet"})
public class DescargaServlet extends HttpServlet {

    private static final String DOWNLOAD_DIR = "C:\\arred";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Seguridad: Solo usuarios logueados pueden descargar
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioId") == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado");
            return;
        }

        // 2. Obtener nombre de archivo
        String fileName = request.getParameter("archivo");
        if (fileName == null || fileName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el nombre del archivo");
            return;
        }
        
        // Evitar Path Traversal (seguridad básica)
        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
             response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre de archivo inválido");
             return;
        }

        File file = new File(DOWNLOAD_DIR + File.separator + fileName);

        // 3. Verificar existencia
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "El archivo no existe en el servidor.");
            return;
        }

        // 4. Configurar respuesta para descarga
        response.setContentType("application/pdf"); // Asumimos PDF, o usar getServletContext().getMimeType(fileName)
        response.setContentLength((int) file.length());
        
        // Header para forzar descarga o mostrar inline
        // "inline" abre en el navegador, "attachment" fuerza guardar. Usaremos inline para PDF.
        String headerKey = "Content-Disposition";
        String headerValue = String.format("inline; filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);

        // 5. Escribir archivo en la respuesta
        try (FileInputStream inStream = new FileInputStream(file);
             OutputStream outStream = response.getOutputStream()) {
             
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
    }
}