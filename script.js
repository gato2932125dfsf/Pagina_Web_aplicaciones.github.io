// Resalta el enlace activo de la navbar según la página
const links = document.querySelectorAll('.nav-links a');
links.forEach(link => {
    if(link.href === window.location.href){
        link.classList.add('active');
    }
});

// Animación simple al cargar los cards
document.addEventListener('DOMContentLoaded', () => {
    const cards = document.querySelectorAll('.card, .project-card');
    cards.forEach((card, index) => {
        card.style.opacity = 0;
        setTimeout(() => {
            card.style.transition = "opacity 0.6s ease-in-out, transform 0.6s ease-in-out";
            card.style.opacity = 1;
            card.style.transform = "translateY(0)";
        }, index * 100);
    });
});
