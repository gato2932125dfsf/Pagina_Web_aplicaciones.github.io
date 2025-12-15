<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    com.gestortes.modelo.Tesis tesis =
        (com.gestortes.modelo.Tesis) request.getAttribute("tesis");

    int progreso = 0;
    String est = (tesis != null) ? tesis.getEstado() : "";
    if("Asignado".equals(est)) progreso = 10;
    else if("En Revisión".equals(est)) progreso = 50;
    else if("Observado".equals(est)) progreso = 30;
    else if("Aprobado".equals(est)) progreso = 100;
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Portal Estudiante</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">

<style>
:root{
    --c-dark:#42313A;
    --c-red:#6C2D2C;
    --c-terra:#9F4636;
    --c-cream:#F1DCC9;
}

body{
    font-family:Inter,sans-serif;
    background:#f4f4f4;
    color:var(--c-dark);
}

/* SIDEBAR */
.sidebar{
    width:250px;
    height:100vh;
    position:fixed;
    background:#fff;
    border-right:1px solid #ddd;
}
.sidebar-brand{
    padding:2rem;
    text-align:center;
    border-bottom:1px solid #eee;
}
.nav-link{
    padding:1rem 2rem;
    color:#555;
    font-weight:500;
}
.nav-link.active{
    background:var(--c-cream);
    color:var(--c-red);
    font-weight:700;
}

/* MAIN */
.main-content{
    margin-left:250px;
    padding:3rem;
}

/* CARDS */
.info-card{
    background:#fff;
    border-radius:14px;
    box-shadow:0 6px 18px rgba(0,0,0,.08);
}

.label{
    font-size:.75rem;
    color:#888;
    text-transform:uppercase;
    letter-spacing:.5px;
}

.progress{
    height:6px;
}

/* TIMELINE */
.timeline-item{
    padding-left:2rem;
    border-left:2px solid #ddd;
    position:relative;
    margin-bottom:2rem;
}
.timeline-item::before{
    content:"";
    position:absolute;
    left:-6px;
    top:4px;
    width:10px;
    height:10px;
    background:var(--c-terra);
    border-radius:50%;
}
</style>
</head>

<body>

<!-- SIDEBAR -->
<aside class="sidebar d-flex flex-column">
    <div class="sidebar-brand">
        <i class="bi bi-mortarboard fs-1 text-secondary"></i>
        <h5 class="mt-2 fw-bold">Estudiante</h5>
    </div>

    <div class="nav flex-column nav-pills mt-4">
        <button class="nav-link active" data-bs-toggle="pill" data-bs-target="#avance-tab">
            Avance
        </button>
        <button class="nav-link" data-bs-toggle="pill" data-bs-target="#entregas-tab">
            Entregas
        </button>
        <button class="nav-link" data-bs-toggle="pill" data-bs-target="#historial-tab">
            Historial
        </button>
    </div>

    <div class="mt-auto p-4">
        <a href="LogoutServlet" class="btn btn-outline-dark w-100">
            Cerrar Sesión
        </a>
    </div>
</aside>

<!-- MAIN -->
<main class="main-content">
<div class="tab-content">

<!-- AVANCE -->
<div class="tab-pane fade show active" id="avance-tab">
    <h3 class="fw-bold mb-4">Mi Proyecto de Tesis</h3>

    <c:if test="${not empty tesis}">
        <div class="row g-4">

            <!-- TITULO -->
            <div class="col-12">
                <div class="info-card p-4">
                    <div class="label mb-1">Título del Proyecto</div>
                    <div class="fw-semibold">
                        ${tesis.titulo}
                    </div>
                </div>
            </div>

            <!-- ESTADO -->
            <div class="col-md-4">
                <div class="info-card p-4 h-100">
                    <div class="label">Estado</div>
                    <div class="mt-2 fw-bold text-uppercase"
                         style="color:var(--c-red)">
                        ${tesis.estado}
                    </div>
                </div>
            </div>

            <!-- DOCENTE -->
            <div class="col-md-4">
                <div class="info-card p-4 h-100">
                    <div class="label">Docente Asesor</div>
                    <div class="fw-bold mt-2">
                        ID ${tesis.docenteId}
                    </div>
                </div>
            </div>

            <!-- PROGRESO -->
            <div class="col-md-4">
                <div class="info-card p-4 h-100">
                    <div class="label mb-2">Avance General</div>
                    <div class="progress">
                        <div class="progress-bar"
                             style="width:<%= progreso %>%;
                                    background:var(--c-red)">
                        </div>
                    </div>
                    <small class="text-muted d-block mt-2">
                        <%= progreso %>% completado
                    </small>
                </div>
            </div>

            <!-- ACCION -->
            <div class="col-md-6">
                <div class="info-card p-4">
                    <h6 class="fw-bold">Nueva Entrega</h6>
                    <p class="text-muted small">
                        Si realizaste correcciones, envía una nueva versión del documento.
                    </p>
                    <button class="btn w-100"
                            style="background:var(--c-red);color:#fff"
                            onclick="document.querySelector('[data-bs-target=\'#entregas-tab\']').click()">
                        Ir a Entregas
                    </button>
                </div>
            </div>

        </div>
    </c:if>

    <c:if test="${empty tesis}">
        <div class="alert alert-warning">
            No tienes un proyecto de tesis asignado.
        </div>
    </c:if>
</div>

<!-- ENTREGAS -->
<div class="tab-pane fade" id="entregas-tab">
    <h3 class="fw-bold mb-4">Nueva Entrega</h3>

    <div class="info-card p-5 text-center">
        <i class="bi bi-file-earmark-pdf fs-1 text-muted"></i>
        <h5 class="mt-3">Subir Archivo PDF</h5>

        <form action="AlumnoController" method="POST"
              enctype="multipart/form-data" class="mt-3">
            <input type="hidden" name="accion" value="subirTesis">
            <input type="file" name="archivo"
                   class="form-control mb-3"
                   accept=".pdf" required>
            <button class="btn"
                    style="background:var(--c-terra);color:#fff">
                Enviar a Revisión
            </button>
        </form>
    </div>
</div>

<!-- HISTORIAL -->
<div class="tab-pane fade" id="historial-tab">
    <h3 class="fw-bold mb-4">Historial</h3>

    <div class="info-card p-5">
        <c:forEach var="h" items="${historial}">
            <div class="timeline-item">
                <div class="fw-bold">
                    Versión ${h.numeroVersion} - ${h.estadoVersion}
                </div>
                <div class="small text-muted">
                    <fmt:formatDate value="${h.fechaSubida}" pattern="dd/MM HH:mm"/>
                </div>

                <c:if test="${not empty h.comentariosDocente}">
                    <p class="fst-italic mt-1">
                        "${h.comentariosDocente}"
                    </p>
                </c:if>

                <c:if test="${h.puntajeTotal > 0}">
                    <div class="fw-bold text-success">
                        Nota: ${h.puntajeTotal}
                    </div>
                </c:if>
            </div>
        </c:forEach>

        <c:if test="${empty historial}">
            <p class="text-center text-muted">
                Aún no hay historial de revisiones.
            </p>
        </c:if>
    </div>
</div>

</div>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
