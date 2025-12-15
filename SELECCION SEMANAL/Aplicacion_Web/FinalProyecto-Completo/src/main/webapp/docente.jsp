<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:useBean id="now" class="java.util.Date" />
<c:set var="limitMillis" value="${7 * 24 * 60 * 60 * 1000}" />

<%
    String nombreUsuario = (String) session.getAttribute("nombre");
    if (nombreUsuario == null) { nombreUsuario = "Docente"; }

    java.util.List<com.gestortes.modelo.Tesis> lista = (java.util.List) request.getAttribute("listaTesis");
    int pendientes = 0, total = (lista != null) ? lista.size() : 0, aprobados = 0, observados = 0;

    if (lista != null) {
        for (com.gestortes.modelo.Tesis t : lista) {
            if ("En Revisión".equals(t.getEstado())) pendientes++;
            if ("Aprobado".equals(t.getEstado())) aprobados++;
            if ("Observado".equals(t.getEstado())) observados++;
        }
    }

    int pctPendiente = (total > 0) ? (int)((double)pendientes/total * 100) : 0;
    int pctAvance = (total > 0) ? (int)((double)aprobados/total * 100) : 0;
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<title>Panel Docente — Gestión</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

<style>
:root{
  --bg-beige:#F1DCC9;
  --dark:#42313A;
  --maroon:#6C2D2C;
  --accent:#9F4636;
  --card-radius:14px;
  --sidebar-w:110px;
}

*{box-sizing:border-box;font-family:'Poppins',sans-serif}
html,body{height:100%;margin:0;background:var(--bg-beige);color:var(--dark)}

/* Layout */
.app { display:flex; min-height:100vh; }
.sidebar {
  width:var(--sidebar-w); background:#fff; border-right:3px solid rgba(0,0,0,0.06);
  display:flex; flex-direction:column; align-items:center; padding:18px 8px; gap:12px;
}
.brand { font-size:12px; font-weight:700; writing-mode:vertical-rl; transform:rotate(180deg); color:var(--dark); margin-bottom:6px; text-align:center;}
.side-btn { width:100%; background:transparent; border:1px solid transparent; padding:10px; border-radius:12px; display:flex; align-items:center; justify-content:center; gap:8px; flex-direction:column; color:var(--dark); text-decoration:none; font-size:12px; cursor:pointer; }
.side-btn i { font-size:22px; color:var(--maroon); }
.side-btn.active, .side-btn:hover { background: rgba(159,70,54,0.08); border-color: rgba(0,0,0,0.04); }

.logout { margin-top:auto; width:100%; }

.main { flex:1; display:flex; flex-direction:column; }

/* Topbar */
.topbar { height:72px; background:#fff; display:flex; align-items:center; justify-content:space-between; padding:10px 20px; border-bottom:3px solid rgba(0,0,0,0.06); }
.page-title { font-weight:700; color:var(--dark); font-size:18px; }
.notif { display:flex; align-items:center; gap:14px; }

/* Content */
.content { padding:22px; overflow-y:auto; }

/* Small stat cards (top) */
.stats-row { display:flex; gap:14px; margin-bottom:14px; align-items:stretch; }
.stat { flex:1; background:#fff; border-radius:10px; padding:14px 18px; box-shadow:0 8px 18px rgba(0,0,0,0.05); display:flex; justify-content:space-between; align-items:center; border-left:6px solid rgba(0,0,0,0.02); }
.stat .num { font-size:26px; font-weight:700; color:var(--dark) }
.stat .label { font-size:12px; color:#6b6b6b }

/* Section headings like in image */
.section { background:var(--bg-beige); margin-bottom:18px; border-radius:6px; padding:8px 0 0 0; }
.section h3 { display:inline-block; background:#fff; padding:6px 12px; border-radius:4px; font-size:20px; color:var(--dark); margin:0 0 12px 18px; }

/* Card white containers */
.card-white { background:#fff; border-radius:8px; padding:12px; box-shadow:0 6px 18px rgba(0,0,0,0.04); border:1px solid rgba(0,0,0,0.03); margin:0 18px 18px 18px; }

/* Tables */
.table thead th { background:#fafafa; font-weight:700; color:#333; font-size:12px; }
.table tbody td { padding:10px 12px; vertical-align:middle; }

/* Actions */
.actions .btn { width:34px; height:34px; padding:0; display:inline-flex; align-items:center; justify-content:center; border-radius:6px; }

/* Report cards row (like "Gestión Descarga") */
.reports { display:flex; gap:14px; margin-top:12px; }
.report { flex:1; background:#fff; border-radius:8px; padding:14px; border:1px solid rgba(0,0,0,0.03); display:flex; flex-direction:column; justify-content:space-between; }

/* Buttons color */
.btn-primary { background:var(--accent); border-color:var(--accent); color:#fff; }
.btn-primary:hover { background:var(--maroon); border-color:var(--maroon); }

/* Badges */
.badge.aprobado { background:#dcfce7; color:#14532d; border:1px solid rgba(34,197,94,0.08); }
.badge.info { background:#fef3e6; color:#7a2f24; }

/* Modal header accent */
.modal-header { background:var(--maroon); color:#fff; }

/* small notification badge */
.notification-badge { position:absolute; top:-6px; right:-6px; padding:0.22em 0.45em; font-size:0.65rem; }

/* Responsive */
@media (max-width: 1000px) {
  .sidebar { width:72px; }
  .stats-row { flex-direction:column; }
  .reports { flex-direction:column; }
}
</style>
</head>
<body>

<div class="app">
  <aside class="sidebar" aria-label="Sidebar">
    <div class="brand">Docente<br/>Portal</div>

    <!-- keep data-bs-target so our JS can show the right pane -->
    <div class="side-btn active" data-bs-target="#evaluaciones" role="button" aria-pressed="true" aria-controls="evaluaciones">
      <i class="bi bi-file-earmark-text"></i>
      <div style="font-size:11px">Evaluaciones</div>
    </div>

    <div class="side-btn" data-bs-target="#mi-perfil" role="button" aria-pressed="false" aria-controls="mi-perfil">
      <i class="bi bi-person-circle"></i>
      <div style="font-size:11px">Mi perfil</div>
    </div>

    <div class="logout">
      <a class="side-btn" href="LogoutServlet" style="justify-content:center"><i class="bi bi-box-arrow-right"></i><div style="font-size:11px">Salir</div></a>
    </div>

    <!-- Hidden helper button used by report buttons to navigate to Evaluaciones -->
    <button id="link-mis-tesis" type="button" data-bs-target="#evaluaciones" class="d-none"></button>
  </aside>

  <div class="main">
    <header class="topbar">
      <div class="page-title">Gestión Académica — Panel Docente</div>

      <!-- Notificaciones -->
      <div class="notif">
        <div class="dropdown">
          <button id="notifToggle" class="btn btn-link p-0 position-relative" data-bs-toggle="dropdown" aria-expanded="false" title="Notificaciones" style="color:var(--dark);">
            <i class="bi bi-bell" style="font-size:22px;"></i>
            <c:if test="${countNotificaciones > 0}">
              <span class="badge bg-danger rounded-pill notification-badge">${countNotificaciones}</span>
            </c:if>
          </button>

          <ul class="dropdown-menu dropdown-menu-end shadow p-0" aria-labelledby="notifToggle" style="width:320px;">
            <li class="p-3 border-bottom d-flex justify-content-between align-items-center bg-light">
              <h6 class="mb-0 fw-bold text-dark">Notificaciones</h6>
              <c:if test="${countNotificaciones > 0}">
                <a href="DocenteController?accion=marcarLeidas" class="small fw-bold text-decoration-none">Marcar leídas</a>
              </c:if>
            </li>

            <div style="max-height:300px; overflow-y:auto;">
              <c:forEach var="n" items="${listaNotificaciones}">
                <li class="dropdown-item p-3 border-bottom">
                  <div class="d-flex gap-2">
                    <i class="bi ${n.tipo == 'alerta' ? 'bi-exclamation-circle-fill text-danger' : 'bi-info-circle-fill text-primary'} fs-5 mt-1"></i>
                    <div>
                      <p class="mb-1 small text-wrap lh-sm">${n.mensaje}</p>
                      <small class="text-muted" style="font-size:0.7rem;"><fmt:formatDate value="${n.fecha}" pattern="dd/MM HH:mm"/></small>
                    </div>
                  </div>
                </li>
              </c:forEach>

              <c:if test="${empty listaNotificaciones}">
                <li class="p-4 text-center text-muted small">Sin notificaciones.</li>
              </c:if>
            </div>
          </ul>
        </div>

        <div style="text-align:right">
          <div style="font-size:14px; color:var(--maroon); font-weight:600">Prof.<span style="margin-left:6px"><%= nombreUsuario %></span></div>
          <div style="font-size:12px;color:#777">Docente Principal</div>
        </div>
      </div>
      <!-- end notifs -->

    </header>

    <main class="content">

      <!-- TAB CONTENT -->
      <div class="tab-content">

        <!-- EVALUACIONES (por defecto visible) -->
        <div class="tab-pane fade show active" id="evaluaciones" role="tabpanel" aria-labelledby="evaluaciones-tab">

          <div class="stats-row">
            <div class="stat">
              <div>
                <div class="num"><%= total %></div>
                <div class="label">TOTAL ALUMNOS</div>
              </div>
              <div style="opacity:0.12;font-size:40px;"><i class="bi bi-people"></i></div>
            </div>

            <div class="stat">
              <div>
                <div class="num"><%= aprobados %></div>
                <div class="label">APROBADAS</div>
              </div>
              <div style="opacity:0.12;font-size:40px;"><i class="bi bi-check-circle"></i></div>
            </div>

            <div style="flex:1"></div>
          </div>

          <div class="section">
            <h3>Gestión de Tesis</h3>
            <div class="card-white">
              <div class="d-flex justify-content-between align-items-center mb-3">
                <div><strong>Gestión</strong><div class="text-muted small">Listado y acciones</div></div>
                <div class="d-flex gap-2">
                  <input class="form-control form-control-sm global-search" placeholder="Buscar..." style="width:220px">
                </div>
              </div>

              <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                  <thead>
                    <tr><th>Estado</th><th>Proyecto</th><th>Alumno</th><th>Archivo</th><th class="text-end">Acciones</th></tr>
                  </thead>
                  <tbody class="searchable-table" id="docenteTableBody">
                    <c:forEach var="t" items="${listaTesis}">
                      <c:set var="semaforo" value="#10b981" />
                      <c:if test="${t.estado == 'En Revisión' && not empty t.fechaUltimaModificacion}">
                          <c:set var="diff" value="${now.time - t.fechaUltimaModificacion.time}" />
                          <c:if test="${diff > limitMillis}"><c:set var="semaforo" value="#ef4444" /></c:if>
                      </c:if>

                      <tr data-id="${t.id}" data-titulo="${t.titulo}" data-alumno="${t.nombreAlumno}">
                        <td>
                          <c:choose>
                            <c:when test="${t.estado == 'En Revisión'}">
                              <span class="status-badge st-revision"><span style="color:${semaforo}; font-size:1.1rem;">●</span> En Revisión</span>
                            </c:when>
                            <c:when test="${t.estado == 'Aprobado'}"><span class="status-badge st-aprobado">Aprobado</span></c:when>
                            <c:when test="${t.estado == 'Observado'}"><span class="status-badge st-observado">Observado</span></c:when>
                            <c:otherwise><span class="badge info">Asignado</span></c:otherwise>
                          </c:choose>
                        </td>
                        <td style="max-width:560px;"><div class="fw-bold text-truncate" title="${t.titulo}">${t.titulo}</div><small class="text-muted">Ult. Mod: <fmt:formatDate value="${t.fechaUltimaModificacion}" pattern="dd/MM/yyyy"/></small></td>
                        <td>${t.nombreAlumno}</td>
                        <td>
                          <c:choose>
                            <c:when test="${not empty t.archivoActualUrl}">
                              <a href="DescargaServlet?archivo=${t.archivoActualUrl}" target="_blank" class="badge aprobadowrap text-decoration-none" style="background:#fff;border:1px solid rgba(0,0,0,0.05);color:#d21f1f;padding:6px 8px;border-radius:6px;"><i class="bi bi-file-earmark-pdf-fill"></i> PDF</a>
                            </c:when>
                            <c:otherwise><span class="badge" style="background:#f5f5f5;color:#777;border-radius:6px;">Vacío</span></c:otherwise>
                          </c:choose>
                        </td>
                        <td class="text-end actions">
                          <button class="btn btn-outline-secondary btn-action btn-historial" title="Historial" data-bs-toggle="modal" data-bs-target="#modalHistorial"><i class="bi bi-clock-history"></i></button>
                          <c:if test="${t.estado != 'Asignado'}">
                            <button class="btn btn-outline-secondary btn-action btn-acta" title="Acta" data-bs-toggle="modal" data-bs-target="#modalActa"><i class="bi bi-printer"></i></button>
                          </c:if>
                          <button class="btn btn-primary btn-action btn-revisar ms-1" title="Evaluar" data-bs-toggle="modal" data-bs-target="#modalRubrica"><i class="bi bi-pencil-square"></i></button>
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <div class="section">
            <h3>Gestión de Alumnos</h3>
            <div class="card-white">
              <div class="d-flex justify-content-between align-items-center mb-3">
                <div><strong>Directorio</strong><div class="text-muted small">Lista de estudiantes y proyectos</div></div>
                <div class="d-flex gap-2">
                  <input class="form-control form-control-sm global-search" placeholder="Buscar alumno..." style="width:220px">
                </div>
              </div>

              <div class="table-responsive">
                <table class="table table-hover mb-0">
                  <thead><tr><th>Estudiante</th><th>Proyecto</th><th>Estado</th><th class="text-end">Detalles</th></tr></thead>
                  <tbody class="searchable-table">
                    <c:forEach var="t" items="${listaTesis}">
                      <tr data-id="${t.id}">
                        <td class="fw-medium"><div class="d-flex align-items-center gap-2"><div class="rounded-circle bg-light d-flex align-items-center justify-content-center text-secondary" style="width:32px;height:32px;"><i class="bi bi-person-fill"></i></div>${t.nombreAlumno}</div></td>
                        <td class="text-muted small text-truncate" style="max-width:350px;">${t.titulo}</td>
                        <td><span class="badge info">${t.estado}</span></td>
                        <td class="text-end"><button class="btn btn-sm btn-outline-info btn-detalle-alumno" data-bs-toggle="modal" data-bs-target="#modalDetalleAlumno"><i class="bi bi-eye-fill me-1"></i> Ver Calificación</button></td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <div class="section">
            <h3>Gestión Descarga</h3>
            <div class="card-white">
              <div class="reports">
                <div class="report">
                  <div>
                    <h5 class="mb-1">Carga Lectiva</h5>
                    <p class="small text-muted mb-3">Exporte el listado completo de sus estudiantes asignados.</p>
                  </div>
                  <div><button class="btn btn-primary" onclick="exportTableToCSV('docenteTableBody','carga_lectiva.csv')"><i class="bi bi-download me-2"></i> Descargar Excel</button></div>
                </div>

                <div class="report">
                  <div>
                    <h5 class="mb-1">Actas de Evaluación</h5>
                    <p class="small text-muted mb-3">Para imprimir actas, vaya a "Mis Tesis" y use el botón de impresora.</p>
                  </div>
                  <div><button class="btn btn-outline-secondary" onclick="document.getElementById('link-mis-tesis').click()">Ir a Mis Tesis</button></div>
                </div>
              </div>
            </div>
          </div>

        </div> <!-- end evaluaciones -->

        <!-- MI PERFIL (copiado/adaptado de admin.jsp) -->
        <div class="tab-pane fade" id="mi-perfil" role="tabpanel" aria-labelledby="mi-perfil-tab">
          <div class="section">
            <h3>Perfil Docente</h3>
            <div class="card-white p-3">
              <div class="row align-items-center">
                <div class="col-md-4 text-center border-end">
                  <div class="p-4">
                    <div style="width:120px;height:120px;border-radius:60px;background:#f3f3f3;margin:auto;display:flex;align-items:center;justify-content:center">
                      <i class="bi bi-person fs-1 text-muted"></i>
                    </div>
                    <h5 class="mt-3 mb-1"><%= nombreUsuario %></h5>
                    <small class="text-muted">Docente</small>
                    <div class="mt-3 small text-muted">Estado: <span class="badge bg-success">Activo</span></div>
                  </div>
                </div>
                <div class="col-md-8">
                  <div class="p-4">
                    <h6>Configuración de Cuenta</h6>
                    <p class="text-muted small">Solicita cambios en tus credenciales de acceso.</p>
                    <div class="alert alert-info small">Por políticas de seguridad, los cambios serán evaluados por el sistema.</div>
                    <form action="DocenteController" method="POST" enctype="multipart/form-data">
                      <input type="hidden" name="accion" value="actualizarPerfil">
                      <div class="row g-2">
                        <div class="col-md-6 mb-2">
                          <label class="small">Nombre</label>
                          <input id="editarNombre" class="form-control" name="editarNombre" value="<%= nombreUsuario %>" required>
                        </div>
                        <div class="col-md-6 mb-2">
                          <label class="small">Email</label>
                          <input id="editarEmail" class="form-control" name="editarEmail" value="${sessionScope.email != null ? sessionScope.email : ''}" required>
                        </div>
                        <div class="col-md-6 mb-2">
                          <label class="small">Nueva Contraseña (Opcional)</label>
                          <input type="password" class="form-control" name="editarPass">
                        </div>
                        <div class="col-md-6 mb-2">
                          <label class="small">Foto (Opcional)</label>
                          <input type="file" class="form-control form-control-sm" name="fotoPerfil" accept="image/*">
                        </div>
                      </div>
                      <div class="mt-3 d-flex gap-2">
                        <button class="btn btn-primary btn-sm" type="submit">Solicitar Cambio</button>
                        <button type="button" class="btn btn-outline-secondary btn-sm" onclick="document.getElementById('editarEmail').value='';">Limpiar</button>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div> <!-- end mi-perfil -->

      </div> <!-- end tab-content -->

    </main>
  </div>
</div>

<!-- MODALES (se mantienen) -->
<div class="modal fade" id="modalRubrica" tabindex="-1" aria-hidden="true" data-bs-backdrop="static">
  <div class="modal-dialog modal-fullscreen">
    <div class="modal-content border-0">
      <div class="modal-header py-2" style="background:var(--maroon);color:#fff">
        <div class="d-flex align-items-center gap-3">
          <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
          <h5 class="modal-title fw-bold mb-0">Evaluación Académica</h5>
          <div class="vr opacity-50 mx-2"></div>
          <div class="small opacity-75" id="rubricaTitulo">...</div>
        </div>
        <div class="d-flex align-items-center gap-4">
          <div class="text-end"><div class="small opacity-75 text-uppercase" style="font-size:0.7rem;">Puntaje</div><div class="d-flex align-items-center gap-2"><div class="h3 fw-bold mb-0" id="displayPuntaje">0.0</div></div></div>
          <div class="text-end"><div class="small opacity-75 text-uppercase" style="font-size:0.7rem;">Condición</div><div class="badge bg-danger" id="displayCondicion">RECHAZADO</div></div>
        </div>
      </div>

      <form id="formRubrica" action="DocenteController" method="POST" class="d-flex flex-column h-100">
        <input type="hidden" name="accion" value="guardarRevision">
        <input type="hidden" id="rubricaTesisId" name="tesisId">

        <div class="modal-body bg-light p-0">
          <div class="container-fluid h-100 d-flex flex-column p-0">
            <div class="bg-white border-bottom p-2 px-4 d-flex justify-content-between align-items-center shadow-sm z-1">
              <div class="small text-muted"><i class="bi bi-info-circle me-1"></i> Complete 38 criterios.</div>
              <div class="btn-group">
                <button type="button" class="btn btn-sm btn-outline-success" id="btnFillAll">Todo Cumple</button>
                <button type="button" class="btn btn-sm btn-outline-secondary" id="btnClearAll">Limpiar</button>
              </div>
            </div>

            <div class="row g-0 flex-grow-1 overflow-hidden">
              <div class="col-lg-8 h-100 position-relative">
                <div class="rubric-container p-4" style="height:100%; overflow:auto;">
                  <table class="table table-bordered table-hover mb-0 rubric-table">
                    <thead>
                      <tr>
                        <th style="width:50px;" class="text-center">#</th>
                        <th>Criterio</th>
                        <th style="width:70px;" class="text-center text-success bg-success bg-opacity-10">C (1)</th>
                        <th style="width:70px;" class="text-center text-warning bg-warning bg-opacity-10">CP (0.5)</th>
                        <th style="width:70px;" class="text-center text-danger bg-danger bg-opacity-10">NC (0)</th>
                      </tr>
                    </thead>
                    <tbody id="cuerpoRubrica"></tbody>
                  </table>
                </div>
              </div>

              <div class="col-lg-4 h-100 bg-white border-start p-4 d-flex flex-column shadow-sm">
                <div class="mb-4 p-3 bg-light rounded border">
                  <label class="fw-bold text-dark mb-1 small text-uppercase">Plazo</label>
                  <input type="date" name="fechaLimite" class="form-control">
                </div>

                <h6 class="fw-bold text-dark mb-2">Feedback</h6>
                <div class="flex-grow-1 mb-3">
                  <textarea class="form-control h-100 p-3" name="comentarios" placeholder="Observaciones..." required style="resize:none; background-color:#f8fafc;"></textarea>
                </div>

                <button type="submit" class="btn" style="background:var(--accent);color:#fff;padding:12px;border-radius:8px;border:none;">GUARDAR</button>
              </div>
            </div>

          </div>
        </div>
      </form>

    </div>
  </div>
</div>

<div class="modal fade" id="modalDetalleAlumno" tabindex="-1">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content border-0 shadow-lg">
      <div class="modal-header bg-white border-bottom-0"><h5 class="modal-title fw-bold text-dark">Detalle de Calificación</h5><button class="btn-close" data-bs-dismiss="modal"></button></div>
      <div class="modal-body p-0" id="detalleAlumnoContent"></div>
    </div>
  </div>
</div>

<div class="modal fade" id="modalActa" tabindex="-1">
  <div class="modal-dialog modal-lg">
    <div class="modal-content border-0">
      <div class="modal-header no-print" style="background:transparent"><h5 class="modal-title">Vista Previa Acta</h5><button class="btn-close" data-bs-dismiss="modal"></button></div>
      <div class="modal-body p-0" id="actaContent"></div>
    </div>
  </div>
</div>

<div class="modal fade" id="modalHistorial" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content border-0 shadow">
      <div class="modal-header bg-light"><h5 class="modal-title fw-bold text-dark">Historial</h5><button class="btn-close" data-bs-dismiss="modal"></button></div>
      <div class="modal-body p-4" id="historialContent"></div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
function exportTableToCSV(tableId, filename) {
  const table = document.getElementById(tableId);
  if(!table) return;
  const rows = table.querySelectorAll('tr');
  let csvContent = [];
  rows.forEach(row => {
    const cols = row.querySelectorAll('td, th');
    let rowData = [];
    cols.forEach((col, index) => {
      if(index < cols.length - 1) {
        let text = col.innerText.replace(/(\r\n|\n|\r)/gm, " ").replace(/"/g, '""');
        rowData.push('"' + text + '"');
      }
    });
    csvContent.push(rowData.join(","));
  });
  const csvFile = new Blob([csvContent.join("\n")], { type: "text/csv" });
  const downloadLink = document.createElement("a");
  downloadLink.download = filename;
  downloadLink.href = window.URL.createObjectURL(csvFile);
  downloadLink.style.display = "none";
  document.body.appendChild(downloadLink);
  downloadLink.click();
  document.body.removeChild(downloadLink);
}

document.querySelectorAll('.global-search').forEach(function(input) {
  input.addEventListener('keyup', function() {
    const term = this.value.toLowerCase();
    const root = this.closest('.content') || document;
    const tbody = root.querySelector('.searchable-table');
    if(tbody) tbody.querySelectorAll('tr').forEach(r => r.style.display = r.textContent.toLowerCase().includes(term) ? '' : 'none');
  });
});

document.addEventListener('click', function(e) {
  const row = e.target.closest('tr');
  if (!row) return;
  if (e.target.closest('.btn-revisar')) {
    document.getElementById('rubricaTesisId').value = row.dataset.id;
    document.getElementById('rubricaTitulo').textContent = row.dataset.titulo + " | " + row.dataset.alumno;
    document.getElementById('formRubrica').reset();
    updateScore();
  }
  if (e.target.closest('.btn-historial')) {
    const c = document.getElementById('historialContent');
    c.innerHTML = '<div class="text-center py-3"><div class="spinner-border text-primary"></div></div>';
    fetch('DocenteController?accion=verHistorial&tesisId=' + row.dataset.id).then(r => r.text()).then(h => c.innerHTML = h);
  }
  if (e.target.closest('.btn-acta')) {
    const c = document.getElementById('actaContent');
    c.innerHTML = '<div class="text-center py-5"><div class="spinner-border text-secondary"></div></div>';
    fetch('DocenteController?accion=verActa&tesisId=' + row.dataset.id).then(r => r.text()).then(h => c.innerHTML = h);
  }
  if (e.target.closest('.btn-detalle-alumno')) {
    const c = document.getElementById('detalleAlumnoContent');
    c.innerHTML = '<div class="text-center py-5"><div class="spinner-border text-info"></div></div>';
    fetch('DocenteController?accion=verDetalleRubrica&tesisId=' + row.dataset.id).then(r => r.text()).then(h => c.innerHTML = h);
  }
});

// Rubrica items + logic (render and score)
const itemsRubrica = [
  { t: "I. Título", c: "1. Es concordante con las variables de estudio, nivel y alcance de la investigación." },
  { t: "II. Resumen", c: "2. El resumen contempla, objetivo, metodología, resultado principal y conclusión general." }, { t: "", c: "3. El resumen no excede las 250 palabras con presenta entre 4 a 6 palabras claves." },
  { t: "III. Introducción", c: "4. Sintetiza el tema de investigación de forma clara y resumida." },
  { t: "IV. Problema", c: "5. Describe el problema desde el punto de vista científico considerando causas, síntomas y pronósticos." }, { t: "", c: "6. La formulación del problema considera variables y dimensiones." },
  { t: "V. Justificación", c: "7. La justificación social determina la contribución hacia la sociedad." }, { t: "", c: "8. La justificación teórica determina la generalización de los resultados." }, { t: "", c: "9. La justificación metodológica considera las razones de los métodos planteados." },
  { t: "VI. Objetivos", c: "10. El objetivo general tiene relación con el problema y el título." }, { t: "", c: "11. Los objetivos específicos están en relación con los problemas específicos." },
  { t: "VII. Ética", c: "12. Describe las implicancias éticas del estudio basado en principios y normas." },
  { t: "VIII. Marco Teórico", c: "13. Los antecedentes consideran objetivo, metodología, variables, resultados y conclusión." }, { t: "", c: "14. Los antecedentes son artículos y tesis < 5 años antigüedad." }, { t: "", c: "15. Las bases teóricas consideran información de las variables y dimensiones." }, { t: "", c: "16. El marco conceptual considera una descripción breve de variables y términos." },
  { t: "IX. Hipótesis", c: "17. Las hipótesis son claras, dan respuesta a problemas y relación con objetivos." },
  { t: "X. Variables", c: "18. Identifica, clasifica y describe las variables del estudio." }, { t: "", c: "19. Operacionaliza las variables (def. conceptual, operacional, dimensiones, indicadores)." },
  { t: "XI. Metodología", c: "20. Identifica y describe método general y específico." }, { t: "", c: "21. Identifica y describe el tipo de investigación." }, { t: "", c: "22. Identifica y describe el nivel de investigación." }, { t: "", c: "23. Describe el diseño de investigación (manipulación y alcance)." }, { t: "", c: "24. Identifica y describe características de la población." }, { t: "25", c: "Identifica la muestra, cálculo muestral y criterios inclusión/exclusión." }, { t: "", c: "26. Describe técnica e instrumento, confiabilidad y validez." }, { t: "", c: "27. Establece técnicas de procesamiento de datos." }, { t: "", c: "28. Establece procedimiento de contrastación de hipótesis." },
  { t: "XII. Resultados", c: "29. Resultados presentados en tablas/gráficos, explicados e interpretados." }, { t: "", c: "30. Presenta contrastación de hipótesis e interpretación estadística." },
  { t: "XIII. Análisis", c: "31. Se establece y redacta ordenadamente por objetivo/variable." }, { t: "", c: "32. Contrasta similitud/discrepancias con antecedentes." },
  { t: "XIV. Conclusiones", c: "33. Establece nivel de alcance hallado en relación a objetivos." },
  { t: "XV. Recomendaciones", c: "34. Derivadas de conclusiones, propuestas de mejora." },
  { t: "XVI. Referencias", c: "35. Establecidas de acuerdo a norma bibliográfica." },
  { t: "XVII. Anexos", c: "36. Considera anexos exigidos ordenadamente." },
  { t: "XVIII. Forma", c: "37. Considera formato señalado por reglamento." }, { t: "", c: "38. Documento ordenado, ortografía, redacción, formato adecuado." }
];

function renderRubrica() {
  const tbody = document.getElementById('cuerpoRubrica');
  let html = '';
  itemsRubrica.forEach((item, idx) => {
    const i = idx + 1;
    if (item.t) html += '<tr class="section-header"><td colspan="5" style="background:#f1f1f1;font-weight:700;">' + item.t + '</td></tr>';
    html += '<tr><td class="text-center fw-bold text-secondary">' + i + '</td><td>' + item.c + '</td><td class="text-center"><input type="radio" class="form-check-input score-radio" name="criterio_' + i + '" value="1.0" required></td><td class="text-center"><input type="radio" class="form-check-input score-radio" name="criterio_' + i + '" value="0.5"></td><td class="text-center"><input type="radio" class="form-check-input score-radio" name="criterio_' + i + '" value="0.0"></td></tr>';
  });
  tbody.innerHTML = html;
}

function updateScore() {
  let total = 0;
  document.querySelectorAll('.score-radio:checked').forEach(r => total += parseFloat(r.value));
  document.getElementById('displayPuntaje').textContent = total.toFixed(1);
  let badge = document.getElementById('displayCondicion');
  if (total >= 25) { badge.textContent = "APROBADO"; badge.className = "badge bg-success"; }
  else if (total >= 13) { badge.textContent = "OBSERVADO"; badge.className = "badge bg-warning text-dark"; }
  else { badge.textContent = "RECHAZADO"; badge.className = "badge bg-danger"; }
}

document.addEventListener('DOMContentLoaded', function(){
  renderRubrica();
  const formRubrica = document.getElementById('formRubrica');
  if(formRubrica) formRubrica.addEventListener('change', function(e){ if(e.target.classList.contains('score-radio')) updateScore(); });
  const btnFillAll = document.getElementById('btnFillAll');
  if(btnFillAll) btnFillAll.addEventListener('click', function(){ document.querySelectorAll('input[value=\"1.0\"]').forEach(r=>r.checked=true); updateScore(); });
  const btnClearAll = document.getElementById('btnClearAll');
  if(btnClearAll) btnClearAll.addEventListener('click', function(){ document.querySelectorAll('.score-radio').forEach(r=>r.checked=false); updateScore(); });

  // Sidebar visual + tab switching (FIX: ahora muestra el pane correcto)
  document.querySelectorAll('.side-btn').forEach(function(btn){
    btn.addEventListener('click', function(e){
      // visual active on buttons
      document.querySelectorAll('.side-btn').forEach(function(b){ b.classList.remove('active'); b.setAttribute('aria-pressed','false'); });
      this.classList.add('active');
      this.setAttribute('aria-pressed','true');

      // find target pane from data-bs-target attribute
      const targetSelector = this.getAttribute('data-bs-target');
      if (!targetSelector) return;

      // hide other panes and show target
      document.querySelectorAll('.tab-pane').forEach(function(p){
        p.classList.remove('show','active');
        p.setAttribute('aria-hidden','true');
      });
      const targetPane = document.querySelector(targetSelector);
      if (targetPane) {
        targetPane.classList.add('show','active');
        targetPane.setAttribute('aria-hidden','false');
      }

      // update aria-selected on possible nav links (for accessibility)
      document.querySelectorAll('.tab-pane').forEach(function(p){
        const id = p.id;
        const control = document.querySelector('[data-bs-target="#' + id + '"]');
        if (control) {
          if (p.classList.contains('active')) control.setAttribute('aria-selected','true'); else control.setAttribute('aria-selected','false');
        }
      });
    });
  });

  // ensure initial pane state (in case markup altered)
  const initialBtn = document.querySelector('.side-btn.active') || document.querySelector('.side-btn[data-bs-target="#evaluaciones"]');
  if(initialBtn) initialBtn.click();
});
</script>
</body>
</html>
