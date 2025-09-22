// ----------------------------Encabezado---------------------------
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

const boton = document.getElementById('aceptarBtn');

let registrarBedel = async () => {
    limpiarMensajeError();

    let bedel = {
        nombre: document.getElementById("nombre").value,
        apellido: document.getElementById("apellido").value,
        usuario: document.getElementById("usuario").value.trim(),
        contrasenia: document.getElementById("contrasenia").value,
        confContrasenia: document.getElementById("confContrasenia").value,
        email: document.getElementById("email").value,
        turno: parseInt(document.getElementById("turno").value.replace(/\D/g, ''))
    };

    // Validaciones de campos, muestra un error en el campo mensaje error 

    if (!bedel.nombre || !bedel.apellido || !bedel.usuario || !bedel.contrasenia || !bedel.confContrasenia || !bedel.email || !bedel.turno) {
        mensajeError.textContent = "Error: todos los campos son obligatorios.";
        return;
    }

    if (bedel.contrasenia !== bedel.confContrasenia) {
        mensajeError.textContent = "Error: las contraseñas no coinciden.";
        return;
    }

    if (bedel.usuario.length < 5 || bedel.usuario.length > 30) {
        mensajeError.textContent = "Error: El usuario debe tener entre 5 y 30 caracteres.";
        return;
    }

    if (bedel.nombre.length > 40) {
        mensajeError.textContent = "Error: El nombre debe tener menos de 40 caracteres.";
        return;
    }

    if (bedel.apellido.length > 40) {
        mensajeError.textContent = "Error: El apellido debe tener menos de 40 caracteres.";
        return;
    }

    try {
        limpiarMensajeError();
        const peticion = await fetch("http://localhost:4400/admin/api/postBedel", {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bedel)
        });

        // Manejo de respuestas
        if (peticion.status === 409) {
            // 409 CONFLICTO
            const errorData = await peticion.json();
            boton.className = "btn-aceptar-modal-error"
            mostrarResultadoModal(errorData.mensaje);
        } else if (peticion.status === 400) {
            // 400 BAD REQUEST
            const errorData = await peticion.json();
            boton.className = "btn-aceptar-modal-error"
            mostrarResultadoModal(errorData.mensaje);
        } else if (peticion.status === 200 || peticion.status === 201) {
            // 200 o 201 ÉXITO
            const respuesta = await peticion.json();
            boton.className = "btn-aceptar-modal-exito"
            mostrarResultadoModal(respuesta.mensaje);
            resetearFormulario();
        } else if (peticion.status === 500) {
            // 500 INTERNAL SERVER ERROR
            boton.className = "btn-aceptar-modal-error"
            mostrarResultadoModal("Error en el servidor. Inténtalo nuevamente más tarde.");
        } else {
            boton.className = "btn-aceptar-modal-error"
            mostrarResultadoModal("Error inesperado: " + peticion.status);
        }
        
    } catch (error) {
        console.log("Error en la petición: " + error.message);
    }
    
};

const resetearFormulario = () => {
    document.getElementById("nombre").value = '';
    document.getElementById("apellido").value = '';
    document.getElementById("usuario").value = '';
    document.getElementById("contrasenia").value = '';
    document.getElementById("confContrasenia").value = '';
    document.getElementById("email").value = '';
    document.getElementById("turno").selectedIndex = 0;
};

const limpiarMensajeError = () => {
    mensajeError.textContent = "";
}

// Función para mostrar el modal
const mostrarResultadoModal = (mensaje) => {
    document.getElementById("resultadoMensaje").textContent = mensaje;
    document.getElementById("resultadoModal").style.display = "block";
};

// Cerrar el modal
document.getElementById("cerrarModal").onclick = function() {
    document.getElementById("resultadoModal").style.display = "none";
};

document.getElementById("aceptarBtn").onclick = function() {
    document.getElementById("resultadoModal").style.display = "none";
};