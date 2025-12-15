<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Sistema Académico — Login</title>

<!-- Google Font + Bootstrap Icons -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

<style>
  :root{
    --bg-beige:#f0dccb;
    --panel-maroon:#9b3f2c;
    --accent-blue:#4fb3d3;
    --card-radius:18px;
  }
  *{box-sizing:border-box;font-family:'Poppins',sans-serif}
  html,body{height:100%;margin:0;background:var(--bg-beige);}
  .wrap {
    min-height:100vh;
    display:flex;
    align-items:center;
    justify-content:center;
    padding:24px;
  }

  /* Card container similar to screenshot */
  .login-card {
    width:920px;
    max-width:95%;
    height:420px;
    display:flex;
    border-radius:var(--card-radius);
    overflow:hidden;
    box-shadow: 0 18px 36px rgba(0,0,0,0.12);
    background:transparent;
  }

  .left-panel {
    flex:1;
    background:var(--panel-maroon);
    color:#fff;
    padding:48px 46px;
    display:flex;
    flex-direction:column;
    justify-content:center;
  }
  .left-panel h1{font-size:28px;margin:0 0 10px 0;font-weight:600}
  .left-panel p{margin:0;color:rgba(255,255,255,0.9);font-size:14px}

  .right-panel {
    width:420px;
    background:#fff;
    padding:28px 32px;
    display:flex;
    flex-direction:column;
    justify-content:center;
    border-radius:0 18px 18px 0;
  }

  .welcome {
    color:var(--accent-blue);
    font-weight:600;
    font-size:20px;
    margin-bottom:4px;
  }
  .sub { font-size:13px;color:#6b6b6b;margin-bottom:18px }

  .form-group { margin-bottom:14px; }
  label { display:block;font-size:13px;color:#333;margin-bottom:6px;font-weight:500 }
  input[type="text"], input[type="password"] {
    width:100%;
    padding:10px 12px;
    border-radius:10px;
    border:1px solid #ccc;
    font-size:14px;
    outline:none;
  }
  input:focus { border-color:var(--panel-maroon); box-shadow:0 4px 14px rgba(0,0,0,0.04) }
  .password-wrapper { position:relative; }
  .password-wrapper .bi { position:absolute; right:12px; top:50%; transform:translateY(-50%); cursor:pointer; color:#666; }

  .btn-enter {
    margin-top:8px;
    width:100%;
    padding:10px;
    border-radius:20px;
    background:#234f84;
    color:#fff;
    border:none;
    font-weight:600;
    cursor:pointer;
  }
  .btn-enter:hover{ background:#193d67 }

  .login-footer { font-size:11px;color:#8a8a8a;text-align:center;margin-top:12px }

  /* Demo/autofill and forgot */
  .row-actions { display:flex; justify-content:space-between; align-items:center; gap:8px; margin-top:10px }
  .demo-buttons button { padding:6px 8px; font-size:12px; border-radius:8px; border:1px solid #ddd; background:#fafafa; cursor:pointer }
  .forgot { font-size:12px;color:var(--panel-maroon); text-decoration:none }

  /* Responsive */
  @media (max-width:900px){
    .login-card { flex-direction:column; height:auto; }
    .left-panel{ padding:28px; border-radius:18px 18px 0 0 }
    .right-panel{ width:100%; border-radius:0 0 18px 18px; padding:20px }
  }
</style>
</head>
<body>
<div class="wrap">
  <div class="login-card">

    <!-- IZQUIERDA (estética similar a la imagen) -->
    <div class="left-panel" aria-hidden="true">
      <h1>Sistema Academico</h1>
      <p>Gestión de evaluaciones y tesis</p>
    </div>

    <!-- DERECHA (FORM) -->
    <div class="right-panel">

      <!-- mensajes de error / ok (mantener compatibilidad con tu flujo) -->
      <% if (request.getAttribute("error") != null) { %>
        <div style="background:#ffe6e6;border:1px solid #f0a9a9;color:#8a1a1a;padding:10px;border-radius:8px;margin-bottom:10px;">
          <i class="bi bi-exclamation-triangle-fill"></i>
          <span style="margin-left:8px;"><%= request.getAttribute("error") %></span>
        </div>
      <% } %>
      <% if (request.getParameter("msg") != null) { %>
        <div style="background:#e9f7ef;border:1px solid #bfe6c8;color:#176f2c;padding:10px;border-radius:8px;margin-bottom:10px;">
          <i class="bi bi-check-circle-fill"></i>
          <span style="margin-left:8px;"><%= request.getParameter("msg") %></span>
        </div>
      <% } %>

      <div class="welcome">Welcome Back!</div>
      <div class="sub">Ingresa tus credenciales para continuar</div>

      <!-- FORMULARIO: Nombres EXACTOS que espera tu LoginServlet -->
      <form id="loginForm" action="LoginServlet" method="POST" autocomplete="off" onsubmit="return validateForm()">

        <div class="form-group">
          <label for="loginId">Correo / Código / DNI</label>
          <input type="text" id="loginId" name="loginId" placeholder="ej. admin@univ.edu o T01244A" required>
        </div>

        <div class="form-group password-wrapper">
          <label for="loginPassword">Contraseña</label>
          <input type="password" id="loginPassword" name="loginPassword" placeholder="Ingrese contraseña" required>
          <i class="bi bi-eye" id="togglePwd" title="Mostrar/ocultar" onclick="togglePassword()"></i>
        </div>

        <div class="row-actions">
          <div class="demo-buttons" aria-hidden="true">
            <!-- Botones demo/autofill (ejecutan sobre ids que usa el servlet) -->
            <button type="button" onclick="fillLogin('admin@univ.edu','admin123')">Admin</button>
            <button type="button" onclick="fillLogin('12345678','docente456')">Docente</button>
            <button type="button" onclick="fillLogin('T01244A','T01244A')">Alumno</button>
          </div>

          <a href="#" class="forgot" data-bs-toggle="modal" data-bs-target="#recoverModal">¿Olvidó su contraseña?</a>
        </div>

        <button type="submit" class="btn-enter">Ingresar</button>

      </form>

      <div class="login-footer">© 2025 Sistemas y Computación — V 1.0</div>

    </div>
  </div>
</div>

<!-- Modal recuperación (mantén la acción que usa tu proyecto) -->
<div class="modal fade" id="recoverModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content" style="border-radius:12px;overflow:hidden">
      <div style="background:var(--panel-maroon);color:#fff;padding:12px 16px;font-weight:600">Recuperación de cuenta</div>
      <div style="padding:16px">
        <p style="margin:0 0 12px 0;color:#333;font-size:13px">Ingrese su DNI / Código o correo para solicitar restablecimiento.</p>
        <form action="LoginServlet" method="POST">
          <input type="hidden" name="accion" value="solicitarRecuperacion">
          <div style="display:flex;gap:8px">
            <input type="text" name="recuperacionInput" placeholder="DNI, Código o correo" style="flex:1;padding:8px;border-radius:8px;border:1px solid #ddd" required>
            <button type="submit" style="background:var(--panel-maroon);color:#fff;padding:8px 12px;border:none;border-radius:8px">Solicitar</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<!-- Bootstrap bundle (para modal) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
  // Toggle password
  function togglePassword(){
    const p = document.getElementById('loginPassword');
    p.type = (p.type === 'password') ? 'text' : 'password';
    const ic = document.getElementById('togglePwd');
    ic.classList.toggle('bi-eye');
    ic.classList.toggle('bi-eye-slash');
  }

  // Demo autofill (usa ids que el servlet lee)
  function fillLogin(u,p){
    document.getElementById('loginId').value = u;
    document.getElementById('loginPassword').value = p;
  }

  // Simple client validation to avoid empty submit
  function validateForm(){
    const id = document.getElementById('loginId').value.trim();
    const pw = document.getElementById('loginPassword').value.trim();
    if(!id || !pw){
      alert('Complete ambos campos.');
      return false;
    }
    return true;
  }
</script>
</body>
</html>
