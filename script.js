// ===============================
// Resaltar enlace activo en navbar
// ===============================
const links = document.querySelectorAll('.nav-links a');
links.forEach(link => {
    // Comparar solo pathname para evitar problemas con parámetros o hashes
    if (link.pathname === window.location.pathname) {
        link.classList.add('active');
    }
});

// ===============================
// Animación simple al cargar cards
// ===============================
document.addEventListener('DOMContentLoaded', () => {
    const cards = document.querySelectorAll('.card, .project-card');
    cards.forEach((card, index) => {
        card.style.opacity = 0;
        card.style.transform = "translateY(20px)"; // efecto de subida inicial
        setTimeout(() => {
            card.style.transition = "opacity 0.6s ease, transform 0.6s ease";
            card.style.opacity = 1;
            card.style.transform = "translateY(0)";
        }, index * 100); // delay escalonado
    });
});
