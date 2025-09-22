document.addEventListener('DOMContentLoaded', () => {
    const nombreUsuario = document.getElementById('usuario-nombre');

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
            if (data.mensaje) {
                nombreUsuario.textContent = data.mensaje;
              } else {
                console.warn('La respuesta no contiene un campo "mensaje".');
              }
        })
        .catch(error => {
            console.error('Error al obtener el usuario autenticado:', error);
        });
});