document.addEventListener('DOMContentLoaded', () => {
    const welcomeTitle = document.getElementById('titulo-current-user');

    fetch('/current/api/user', {
        method: 'GET',
        credentials: 'include',
    })
        .then(response => {
            if (!response.ok) {
                response.json().then(data => {
                    throw new Error(data.mensaje || 'No se pudo obtener la información del usuario por un error desconocido, por favor contactar con soporte.');
                });
            }
            return response.json();
        })
        .then(data => {
            welcomeTitle.textContent = `Bienvenido ${data.mensaje}`;
        })
        .catch(error => {
            console.error('Error al obtener el usuario autenticado:', error);
        });
});