<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
String nombreUsuario = (String) session.getAttribute("nombre");
if (nombreUsuario == null) { nombreUsuario = "Administrador"; }

// stats (compatibilidad con variables que ya usas)
Object totalObj = request.getAttribute("statsTotalTesis");
double total = (totalObj != null) ? Double.parseDouble(totalObj.toString()) : 0;
Object aprobObj = request.getAttribute("statsAprobados");
double aprob = (aprobObj != null) ? Double.parseDouble(aprobObj.toString()) : 0;
Object obsObj = request.getAttribute("statsObservados");
double obs = (obsObj != null) ? Double.parseDouble(obsObj.toString()) : 0;
int pctAprob = (total > 0) ? (int)((aprob/total)*100) : 0;
int pctObs = (total > 0) ? (int)((obs/total)*100) : 0;
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Panel Administrativo - Gestión Tesis</title>

<!-- Bootstrap 5 (solo CSS para estilos base) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">

<style>
  /* Paleta del proyecto (según indicaciones) */
  :root{
    --m1:#42313A; /* oscuro */
    --m2:#6C2D2C; /* acento */
    --m3:#9F4636; /* acento claro */
    --bg:#F1DCC9; /* fondo beige */
    --card:#ffffff;
    --sidebar-width:100px;
  }

  body { margin:0; font-family: 'Inter', sans-serif; background:var(--bg); color:#222; }
  .app { display:flex; min-height:100vh; }

  /* SIDEBAR */
  .sidebar {
    width: var(--sidebar-width);
    background: #fff;
    border-right: 2px solid rgba(0,0,0,0.06);
    display:flex;
    flex-direction:column;
    align-items:center;
    padding:18px 8px;
    gap:12px;
  }
  .sidebar .brand { font-size:12px; font-weight:700; writing-mode:vertical-rl; transform:rotate(180deg); color:var(--m1); margin-bottom:6px; }
  .side-btn { width:100%; display:flex; align-items:center; gap:10px; padding:10px; border-radius:10px; cursor:pointer; border:1px solid transparent; text-decoration:none; color:var(--m1); background:transparent; justify-content:center; flex-direction:column; font-size:12px; }
  .side-btn.active, .side-btn:hover { background:rgba(0,0,0,0.03); border-color:rgba(0,0,0,0.06); }
  .side-icon { font-size:22px; color:var(--m2); margin-bottom:6px; }

  /* TOP BAR */
  .main {
    flex:1;
    display:flex;
    flex-direction:column;
  }
  .topbar {
    height:72px;
    background:#fff;
    display:flex;
    align-items:center;
    justify-content:space-between;
    padding:12px 20px;
    border-bottom:1px solid rgba(0,0,0,0.06);
  }
  .top-left { display:flex; align-items:center; gap:12px; }
  .page-title { font-weight:700; color:var(--m1); font-size:18px; }
  .notif { display:flex; align-items:center; gap:12px; }

  /* CONTENT */
  .content {
    padding:22px;
    overflow-y:auto;
  }

  .section {
    background:var(--bg);
    margin-bottom:18px;
    border-radius:8px;
    padding:14px;
    border-left:6px solid rgba(0,0,0,0.03);
  }

  .section h3 {
    display:inline-block;
    background:#fff;
    padding:6px 12px;
    border-radius:4px;
    font-size:20px;
    color:var(--m1);
    margin:0 0 12px 0;
  }

  .card-white {
    background:var(--card);
    border-radius:8px;
    padding:12px;
    box-shadow:0 6px 18px rgba(0,0,0,0.04);
    border:1px solid rgba(0,0,0,0.03);
  }

  /* tablas */
  table.table { margin-bottom:0; }
  .table thead th { background:#fafafa; font-weight:700; color:#333; font-size:12px; }
  .actions .btn { width:34px; height:34px; padding:0; display:inline-flex; align-items:center; justify-content:center; }

  /* responsive */
  @media (max-width: 992px) {
    .sidebar { display:none; }
    .topbar { padding:12px; }
    .content { padding:12px; }
  }
</style>
</head>
<body>

<div class="app">

  <!-- SIDEBAR (vertical) -->
  <nav class="sidebar" aria-label="Sidebar">
    <div class="brand">Administrador<br/>Portal</div>

    <a class="side-btn active" href="#registros" data-bs-toggle="pill">
      <i class="bi bi-people-fill side-icon"></i>
      <div>Registros</div>
    </a>

    <a class="side-btn" href="#gestion-tesis" data-bs-toggle="pill">
      <i class="bi bi-journal-text side-icon"></i>
      <div>Gestión</div>
    </a>

    <a class="side-btn" href="#mi-perfil" data-bs-toggle="pill">
      <i class="bi bi-person-circle side-icon"></i>
      <div>Mi perfil</div>
    </a>

    <div style="flex:1"></div>

    <a class="side-btn" href="LogoutServlet" title="Cerrar Sesion">
      <i class="bi bi-box-arrow-right side-icon"></i>
      <div>Cerrar</div>
    </a>
  </nav>

  <!-- MAIN -->
  <div class="main">
    <!-- TOPBAR -->
    <header class="topbar">
      <div class="top-left">
        <div class="page-title">Gestión Académica — Panel Admin</div>
        <small class="text-muted" style="margin-left:12px">Bienvenido al sistema de administración académica</small>
      </div>

      <div class="notif">
        <div class="me-3">
          <div class="position-relative">
            <i class="bi bi-bell fs-4"></i>
            <c:if test="${countNotificaciones > 0}">
              <span style="position:absolute; top:-6px; right:-8px;" class="badge bg-danger rounded-pill">${countNotificaciones}</span>
            </c:if>
          </div>
        </div>
        <div class="text-end">
          <div style="font-size:14px; color:var(--m2); font-weight:600">Admin.<span style="margin-left:6px">${nombreUsuario}</span></div>
          <div class="text-muted" style="font-size:12px">Administrador</div>
        </div>
      </div>
    </header>

    <!-- CONTENT -->
    <main class="content">
      <div class="tab-content">

        <!-- PESTAÑA REGISTROS -->
        <section class="tab-pane fade show active" id="registros">
          <!-- GESTION DE ALUMNOS -->
          <div class="section">
            <h3>Gestión de Alumnos</h3>
            <div class="card-white mt-3">
              <div class="d-flex justify-content-between align-items-center mb-3">
                <div><strong>Directorio Alumnos</strong><div class="text-muted small">Bienvenido al sistema de administración académica</div></div>
                <div class="d-flex gap-2">
                  <input class="form-control form-control-sm global-search" placeholder="Buscar alumno..." style="width:220px">
                  <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#adminCrearAlumnoModal">+ Crear</button>
                </div>
              </div>

              <div class="table-responsive">
                <table class="table table-hover" id="tablaAlumnosCompleta">
                  <thead>
                    <tr><th>Nombre</th><th>Email</th><th>Código</th><th class="text-end">Acciones</th></tr>
                  </thead>
                  <tbody class="searchable-table" id="alumnoTableBody">
                    <c:forEach var="a" items="${listaAlumnos}">
                      <tr data-id="${a.id}">
                        <td data-field="nombre" class="fw-medium">${a.nombre}</td>
                        <td data-field="email" class="text-muted">${a.email}</td>
                        <td data-field="codigo"><span class="badge bg-light text-dark border">${a.codigo}</span></td>
                        <td class="text-end actions">
                          <button class="btn btn-warning btn-action btn-edit" data-bs-toggle="modal" data-bs-target="#adminEditarAlumnoModal" title="Editar"><i class="bi bi-pencil-fill"></i></button>
                          <a href="AdminController?accion=eliminarAlumno&id=${a.id}" class="btn btn-danger btn-action" onclick="return confirm('¿Eliminar alumno?')" title="Eliminar"><i class="bi bi-trash-fill"></i></a>
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>

            </div>
          </div>

          <!-- GESTION DE DOCENTES -->
          <div class="section">
            <h3>Gestión de Docentes</h3>
            <div class="card-white mt-3">
              <div class="d-flex justify-content-between align-items-center mb-3">
                <div><strong>Directorio Docentes</strong></div>
                <div class="d-flex gap-2">
                  <input class="form-control form-control-sm global-search" placeholder="Buscar docente..." style="width:220px">
                  <button class="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#adminCrearDocenteModal">+ Crear</button>
                </div>
              </div>

              <div class="table-responsive">
                <table class="table table-hover" id="tablaDocentesCompleta">
                  <thead><tr><th>Nombre</th><th>Email</th><th>DNI</th><th class="text-end">Acciones</th></tr></thead>
                  <tbody class="searchable-table" id="docenteTableBody">
                    <c:forEach var="d" items="${listaDocentes}">
                      <tr data-id="${d.id}">
                        <td data-field="nombre" class="fw-medium">${d.nombre}</td>
                        <td data-field="email" class="text-muted">${d.email}</td>
                        <td data-field="dni">${d.dni}</td>
                        <td class="text-end actions">
                          <button class="btn btn-warning btn-action btn-edit" data-bs-toggle="modal" data-bs-target="#adminEditarDocenteModal"><i class="bi bi-pencil-fill"></i></button>
                          <a href="AdminController?accion=eliminarDocente&id=${d.id}" class="btn btn-danger btn-action" onclick="return confirm('¿Eliminar docente?')" title="Eliminar"><i class="bi bi-trash-fill"></i></a>
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>

            </div>
          </div>

          <!-- GESTION DE ADMINISTRADORES -->
          <div class="section">
            <h3>Gestión de Administradores</h3>
            <div class="card-white mt-3">
              <div class="d-flex justify-content-between align-items-center mb-3">
                <div><strong>Administradores</strong></div>
                <button class="btn btn-sm btn-secondary" data-bs-toggle="modal" data-bs-target="#adminCrearAdminModal">+ Crear</button>
              </div>

              <div class="table-responsive">
                <table class="table table-hover mb-0">
                  <thead><tr><th>Nombre</th><th>Email</th><th class="text-end">Acciones</th></tr></thead>
                  <tbody id="adminTableBody">
                    <c:forEach var="admin" items="${listaAdmins}">
                      <tr data-id="${admin.id}">
                        <td data-field="nombre" class="fw-medium">${admin.nombre}</td>
                        <td data-field="email" class="text-muted">${admin.email}</td>
                        <td class="text-end actions">
                          <button class="btn btn-warning btn-action btn-edit" data-bs-toggle="modal" data-bs-target="#adminEditarAdminModal"><i class="bi bi-pencil-fill"></i></button>
                          <a href="AdminController?accion=eliminarAdmin&id=${admin.id}" class="btn btn-danger btn-action" onclick="return confirm('¿Eliminar administrador?')" title="Eliminar"><i class="bi bi-trash-fill"></i></a>
                        </td>
                      </tr>
                    </c:forEach>
                    <c:if test="${empty listaAdmins}">
                      <tr><td colspan="3" class="text-center p-4 text-muted">No hay administradores registrados.</td></tr>
                    </c:if>
                  </tbody>
                </table>
              </div>

            </div>
          </div>

        </section>

        <!-- PESTAÑA GESTION TESIS -->
        <section class="tab-pane fade" id="gestion-tesis">
          <div class="section">
            <h3>Gestión de Tesis</h3>
            <div class="card-white mt-3">
              <div class="d-flex justify-content-between align-items-center mb-3">
                <div><strong>Listado de Tesis</strong></div>
                <div class="d-flex gap-2">
                  <input class="form-control form-control-sm global-search" placeholder="Buscar tesis..." style="width:220px">
                  <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#adminAddTesisModal">+ Asignar</button>
                </div>
              </div>

              <div class="table-responsive">
                <table class="table table-hover mb-0" id="tablaTesisCompleta">
                  <thead><tr><th>Título</th><th>Archivo</th><th>Alumno</th><th>Docente</th><th>Estado</th><th class="text-end">Acciones</th></tr></thead>
                  <tbody class="searchable-table" id="tesisTableBody">
                    <c:forEach var="t" items="${listaTesis}">
                      <tr data-id="${t.id}" data-titulo="${t.titulo}" data-estado="${t.estado}" data-alumno-id="${t.alumnoId}" data-docente-id="${t.docenteId}">
                        <td class="fw-medium text-truncate" style="max-width:260px;" title="${t.titulo}">${t.titulo}</td>
                        <td>
                          <c:choose>
                            <c:when test="${not empty t.archivoActualUrl}">
                              <a href="DescargaServlet?archivo=${t.archivoActualUrl}" target="_blank" class="badge bg-danger text-decoration-none no-print"><i class="bi bi-file-pdf-fill"></i> PDF</a>
                            </c:when>
                            <c:otherwise><span class="badge bg-light text-secondary border">Vacío</span></c:otherwise>
                          </c:choose>
                        </td>
                        <td>${t.nombreAlumno}</td>
                        <td>${t.nombreDocente}</td>
                        <td><span class="badge ${t.estado == 'Aprobado' ? 'bg-success' : t.estado == 'Observado' ? 'bg-warning text-dark' : t.estado == 'Rechazado' ? 'bg-danger' : 'bg-info text-dark'}">${t.estado}</span></td>
                        <td class="text-end actions">
                          <button class="btn btn-warning btn-action btn-edit" data-bs-toggle="modal" data-bs-target="#adminEditarTesisModal" title="Editar"><i class="bi bi-pencil-fill"></i></button>
                          <a href="AdminController?accion=eliminarTesis&id=${t.id}" class="btn btn-danger btn-action" onclick="return confirm('¿Eliminar tesis?')" title="Eliminar"><i class="bi bi-trash-fill"></i></a>
                        </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>

            </div>
          </div>
        </section>

        <!-- PESTAÑA MI PERFIL -->
        <section class="tab-pane fade" id="mi-perfil">
          <div class="section">
            <h3>Mi perfil</h3>
            <div class="card-white mt-3">
              <div class="row">
                <div class="col-md-4">
                  <div class="text-center">
                    <div style="width:120px;height:120px;border-radius:60px;background:#f5f5f5;margin:0 auto 12px;display:flex;align-items:center;justify-content:center">
                      <i class="bi bi-person fs-1 text-muted"></i>
                    </div>
                    <h5 class="mb-0">${nombreUsuario}</h5>
                    <small class="text-muted">Admin</small>
                  </div>
                </div>
                <div class="col-md-8">
                  <div>
                    <h6>Configuración de Cuenta</h6>
                    <p class="text-muted small">Solicita cambios en tus credenciales de acceso.</p>
                    <form action="AdminController" method="POST">
                      <input type="hidden" name="accion" value="actualizarAdmin">
                      <div class="mb-2"><label class="small">Nombre</label><input name="editarAdminNombre" class="form-control" value="${nombreUsuario}" required></div>
                      <div class="mb-2"><label class="small">Email</label><input name="editarAdminEmail" class="form-control" value="" required></div>
                      <div class="mb-2"><label class="small">Nueva Contraseña (Opcional)</label><input type="password" name="editarAdminPass" class="form-control"></div>
                      <button class="btn btn-primary btn-sm">Solicitar Cambio</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>

      </div>
    </main>
  </div>
</div>

<!-- ================= MODALES (SE PRESERVAN LOS MISMO IDs / NAMES QUE EN TU PROYECTO) ================= -->
<!-- Crear Tesis -->
<div class="modal fade" id="adminAddTesisModal" tabindex="-1"><div class="modal-dialog modal-dialog-centered"><div class="modal-content"><div class="modal-header bg-secondary text-white"><h5 class="modal-title">Nueva Tesis</h5><button class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div><div class="modal-body"><form id="crearTesisForm" action="AdminController" method="POST" enctype="multipart/form-data"><input type="hidden" name="accion" value="crearTesis"><input class="form-control mb-2" name="tesisTitulo" placeholder="Título de Tesis" required><div class="mb-2"><label class="form-label small text-muted">Archivo PDF</label><input type="file" class="form-control" name="tesisArchivo" accept=".pdf"></div><select class="form-select mb-2" name="tesisAlumno" required><option value="">Seleccionar Alumno...</option><c:forEach var="a" items="${listaAlumnos}"><option value="${a.id}">${a.nombre}</option></c:forEach></select><select class="form-select" name="tesisDocente" required><option value="">Seleccionar Docente...</option><c:forEach var="d" items="${listaDocentes}"><option value="${d.id}">${d.nombre}</option></c:forEach></select></form></div><div class="modal-footer"><button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button><button type="submit" form="crearTesisForm" class="btn btn-primary">Guardar</button></div></div></div></div>

<!-- Crear Docente -->
<div class="modal fade" id="adminCrearDocenteModal" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-header bg-success text-white"><h5 class="modal-title">Crear Docente</h5><button class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div><div class="modal-body"><form id="crearDocenteForm" action="AdminController" method="POST"><input type="hidden" name="accion" value="crearDocente"><input class="form-control mb-2" name="docenteNombre" placeholder="Nombre Completo" required><input class="form-control mb-2" name="docenteEmail" type="email" placeholder="Email" required><input class="form-control mb-2" name="docenteDNI" placeholder="DNI" required><input class="form-control" name="docentePass" type="password" placeholder="Contraseña" required></form></div><div class="modal-footer"><button type="submit" form="crearDocenteForm" class="btn btn-success">Guardar</button></div></div></div></div>

<!-- Crear Alumno -->
<div class="modal fade" id="adminCrearAlumnoModal" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-header bg-info text-white"><h5 class="modal-title">Crear Alumno</h5><button class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div><div class="modal-body"><form id="crearAlumnoForm" action="AdminController" method="POST"><input type="hidden" name="accion" value="crearAlumno"><input class="form-control mb-2" name="alumnoNombre" placeholder="Nombre Completo" required><input class="form-control mb-2" name="alumnoEmail" type="email" placeholder="Email" required><input class="form-control mb-2" name="alumnoCodigo" placeholder="Código Estudiante" required><input class="form-control" name="alumnoPass" type="password" placeholder="Contraseña" required></form></div><div class="modal-footer"><button type="submit" form="crearAlumnoForm" class="btn btn-info text-white">Guardar</button></div></div></div></div>

<!-- Crear Admin -->
<div class="modal fade" id="adminCrearAdminModal" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-header bg-secondary text-white"><h5 class="modal-title">Crear Admin</h5><button class="btn-close btn-close-white" data-bs-dismiss="modal"></button></div><div class="modal-body"><form id="crearAdminForm" action="AdminController" method="POST"><input type="hidden" name="accion" value="crearAdmin"><input class="form-control mb-2" name="adminNombre" placeholder="Nombre" required><input class="form-control mb-2" name="adminEmail" type="email" placeholder="Email" required><input class="form-control" name="adminPass" type="password" placeholder="Contraseña" required></form></div><div class="modal-footer"><button type="submit" form="crearAdminForm" class="btn btn-secondary">Guardar</button></div></div></div></div>

<!-- Editar Tesis -->
<div class="modal fade" id="adminEditarTesisModal" tabindex="-1"><div class="modal-dialog modal-dialog-centered"><div class="modal-content"><div class="modal-header bg-warning text-dark"><h5 class="modal-title">Editar Tesis</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div><div class="modal-body"><form id="editarTesisForm" action="AdminController" method="POST" enctype="multipart/form-data"><input type="hidden" name="accion" value="actualizarTesis"><input type="hidden" id="editarTesisId" name="editarTesisId"><label class="small fw-bold">Título</label><input class="form-control mb-2" id="editarTesisTitulo" name="editarTesisTitulo" required><div class="row"><div class="col-6"><label class="small fw-bold">Alumno</label><select class="form-select mb-2" id="editarTesisAlumno" name="editarTesisAlumno" required><c:forEach var="a" items="${listaAlumnos}"><option value="${a.id}">${a.nombre}</option></c:forEach></select></div><div class="col-6"><label class="small fw-bold">Docente</label><select class="form-select mb-2" id="editarTesisDocente" name="editarTesisDocente" required><c:forEach var="d" items="${listaDocentes}"><option value="${d.id}">${d.nombre}</option></c:forEach></select></div></div><label class="small fw-bold">Estado</label><select class="form-select mb-3" id="editarTesisEstado" name="editarTesisEstado" required><option value="Asignado">Asignado</option><option value="Observado">Observado</option><option value="Aprobado">Aprobado</option><option value="Rechazado">Rechazado</option></select><div class="p-2 bg-light border rounded"><label class="small text-primary fw-bold">Reemplazar Archivo (Opcional)</label><input type="file" name="editarTesisArchivo" class="form-control form-control-sm" accept=".pdf"></div></form></div><div class="modal-footer"><button type="submit" form="editarTesisForm" class="btn btn-primary">Actualizar</button></div></div></div></div>

<!-- Editar Docente -->
<div class="modal fade" id="adminEditarDocenteModal" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h5 class="modal-title">Editar Docente</h5><button class="btn-close" data-bs-dismiss="modal"></button></div><div class="modal-body"><form id="editarDocenteForm" action="AdminController" method="POST"><input type="hidden" name="accion" value="actualizarDocente"><input type="hidden" id="editarDocenteId" name="editarDocenteId"><label class="small text-muted">Nombre</label><input class="form-control mb-2" id="editarDocenteNombre" name="editarDocenteNombre" required><label class="small text-muted">Email</label><input class="form-control mb-2" id="editarDocenteEmail" name="editarDocenteEmail" required><label class="small text-muted">DNI</label><input class="form-control mb-3" id="editarDocenteDNI" name="editarDocenteDNI" required><div class="p-2 bg-light border rounded"><label class="small fw-bold text-primary">Cambiar Contraseña (Opcional)</label><input type="password" class="form-control form-control-sm" name="editarDocentePass" placeholder="Escriba para cambiar..."></div></form></div><div class="modal-footer"><button type="submit" form="editarDocenteForm" class="btn btn-success">Actualizar</button></div></div></div></div>

<!-- Editar Alumno -->
<div class="modal fade" id="adminEditarAlumnoModal" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h5 class="modal-title">Editar Alumno</h5><button class="btn-close" data-bs-dismiss="modal"></button></div><div class="modal-body"><form id="editarAlumnoForm" action="AdminController" method="POST"><input type="hidden" name="accion" value="actualizarAlumno"><input type="hidden" id="editarAlumnoId" name="editarAlumnoId"><label class="small text-muted">Nombre</label><input class="form-control mb-2" id="editarAlumnoNombre" name="editarAlumnoNombre" required><label class="small text-muted">Email</label><input class="form-control mb-2" id="editarAlumnoEmail" name="editarAlumnoEmail" required><label class="small text-muted">Código</label><input class="form-control mb-3" id="editarAlumnoCodigo" name="editarAlumnoCodigo" required><div class="p-2 bg-light border rounded"><label class="small fw-bold text-primary">Cambiar Contraseña (Opcional)</label><input type="password" class="form-control form-control-sm" name="editarAlumnoPass" placeholder="Escriba para cambiar..."></div></form></div><div class="modal-footer"><button type="submit" form="editarAlumnoForm" class="btn btn-info text-white">Actualizar</button></div></div></div></div>

<!-- Editar Admin -->
<div class="modal fade" id="adminEditarAdminModal" tabindex="-1"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><h5 class="modal-title">Editar Admin</h5><button class="btn-close" data-bs-dismiss="modal"></button></div><div class="modal-body"><form id="editarAdminForm" action="AdminController" method="POST"><input type="hidden" name="accion" value="actualizarAdmin"><input type="hidden" id="editarAdminId" name="editarAdminId"><label class="small text-muted">Nombre</label><input class="form-control mb-2" id="editarAdminNombre" name="editarAdminNombre" required><label class="small text-muted">Email</label><input class="form-control mb-2" id="editarAdminEmail" name="editarAdminEmail" required></form></div><div class="modal-footer"><button type="submit" form="editarAdminForm" class="btn btn-primary">Actualizar</button></div></div></div></div>

<!-- ================= SCRIPTS (PRESERVADAS FUNCIONES ORIGINALES) ================= -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
  // Export CSV / Print (funciones originales)
  function exportTableToCSV(tableId, filename) {
    const table = document.getElementById(tableId);
    if(!table) return;
    const rows = table.querySelectorAll('tr');
    let csvContent = [];
    rows.forEach(row => {
      const cols = row.querySelectorAll('td, th');
      let rowData = [];
      cols.forEach((col, index) => {
        if (index < cols.length - 1) {
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

  function printSection(tabId) {
    const tabContent = document.getElementById(tabId).innerHTML;
    const originalContent = document.body.innerHTML;
    document.body.innerHTML = '<div style="padding:20px"><h2>Reporte Gestión Tesis</h2><hr>' + tabContent + '</div>';
    window.print();
    document.body.innerHTML = originalContent;
    location.reload();
  }

  // Búsqueda global (mantiene comportamiento original)
  (function () {
    document.querySelectorAll('.global-search').forEach(input => {
      input.addEventListener('keyup', function() {
        const term = this.value.toLowerCase();
        const tbody = this.closest('.card-white').querySelector('.searchable-table');
        if(tbody) tbody.querySelectorAll('tr').forEach(r => r.style.display =
          r.textContent.toLowerCase().includes(term) ? '' : 'none');
      });
    });

    document.querySelector('.content').addEventListener('click', function(e) {
      const btn = e.target.closest('.btn-edit');
      if(!btn) return;
      const row = btn.closest('tr');
      const id = row.closest('tbody').id;

      if(id === 'tesisTableBody') {
        document.getElementById('editarTesisId').value = row.dataset.id;
        document.getElementById('editarTesisTitulo').value = row.dataset.titulo;
        document.getElementById('editarTesisAlumno').value = row.dataset.alumnoId;
        document.getElementById('editarTesisDocente').value = row.dataset.docenteId;
        document.getElementById('editarTesisEstado').value = row.dataset.estado;

      } else if(id === 'docenteTableBody') {
        document.getElementById('editarDocenteId').value = row.dataset.id;
        document.getElementById('editarDocenteNombre').value = row.querySelector('[data-field=\"nombre\"]').textContent;
        document.getElementById('editarDocenteEmail').value = row.querySelector('[data-field=\"email\"]').textContent;
        document.getElementById('editarDocenteDNI').value = row.querySelector('[data-field=\"dni\"]').textContent;
        document.querySelector('input[name=\"editarDocentePass\"]').value = '';

      } else if(id === 'alumnoTableBody') {
        document.getElementById('editarAlumnoId').value = row.dataset.id;
        document.getElementById('editarAlumnoNombre').value = row.querySelector('[data-field=\"nombre\"]').textContent;
        document.getElementById('editarAlumnoEmail').value = row.querySelector('[data-field=\"email\"]').textContent;
        document.getElementById('editarAlumnoCodigo').value = row.querySelector('[data-field=\"codigo\"]').textContent;
        document.querySelector('input[name=\"editarAlumnoPass\"]').value = '';

      } else if(id === 'adminTableBody') {
        document.getElementById('editarAdminId').value = row.dataset.id;
        document.getElementById('editarAdminNombre').value = row.querySelector('[data-field=\"nombre\"]').textContent;
        document.getElementById('editarAdminEmail').value = row.querySelector('[data-field=\"email\"]').textContent;
      }
    });
  })();
</script>
</body>
</html>
