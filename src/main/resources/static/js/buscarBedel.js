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

//CONSTANTES GLOBALES

//boton buscar
const boton = document.getElementById('buscar');

//mensaje de error de parametros de busqueda
const mensajeError = document.getElementById("mensajeError");

//Modal de resultados
const modalResultado = document.getElementById("resultadoModal");
const mensajeModalResultado = document.getElementById("resultadoMensaje");
const botonAceptarResultado = document.getElementById("aceptarBtn");


// ----------------------------BUSQUEDA DE BEDELES----------------------------

boton.addEventListener("click", function () {

    //Creamos las variables para el conteiner y el filtro para la busqueda
    const bedelListContainer = document.getElementById("bedel-list");
    const valorBusqueda = document.getElementById("valor_busqueda").value;
    const filtroSeleccionado = document.getElementById("filtro").value;

    limpiarMensajeError();

    // Validamos que si se haya seleccionado un filtro, que el valor de búsqueda no esté vacío
    if (filtroSeleccionado !== "0" && !valorBusqueda) {
        mostrarMensajeError("Por favor, Escriba un valor de búsqueda.");
        return;
    }

    // Limpiar contenedor antes de cargar
    bedelListContainer.innerHTML = "";

    //creamos el filtro a enviar a la API rest
    const filtroDatos = {
        filtro: filtroSeleccionado,
        valorBusqueda: valorBusqueda
    };

    // Comienzo de la petición
    try {
        limpiarMensajeError();

        fetch("/admin/api/getBedel", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(filtroDatos)
        })
            .then(response => {
                //Si la peticion es erronea (Status!=200) se mostrara el modal De resultado con el Mensaje de error
                if (!(response.status === 200 || response.status === 201)) {
                    response.json().then(data => {
                        mostrarModalConMensajeError(data.mensaje || "Ocurrio un error desconocido, por favor contactar con soporte."); 
                    });
                    return;
                }
                //si no hay errores se retorna la lista de bedeles
                return response.json();
            })
            .then(data => { 
                // Limpiamos el contenedor antes de agregar contenido nuevo
                bedelListContainer.innerHTML = "";
            
                // Si no hay coincidencias para el filtro seleccionado, informamos y cortamos la ejecución
                if (data.length === 0) {
                    bedelListContainer.textContent = "No se encontraron bedeles.";
                    return;
                }
            
                // Creamos una tabla para contener la información de los bedeles
                const table = document.createElement("table");
                table.className = "bedel-table";
            
                // Creamos el encabezado de la tabla
                const thead = document.createElement("thead");
                thead.innerHTML = `
                    <tr>
                        <th>ID</th>
                        <th>Usuario</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Turno</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                `;
                table.appendChild(thead);
            
                // Creamos el cuerpo de la tabla
                const tbody = document.createElement("tbody");
            
                // Iteramos todos los bedeles para crear las filas
                data.forEach(bedel => {
                    const row = document.createElement("tr");
                    row.className = "bedel-row";
                    row.dataset.id = bedel.id;
            
                    // Consultamos si el bedel está activo o inactivo para setear el fondo de la fila
                    row.style.backgroundColor = bedel.estado ? "#7be48c" : "#fcf96f";
            
                    // Creamos las celdas con la información del bedel
                    row.innerHTML = `
                        <td>${bedel.id}</td>
                        <td>${bedel.usuario}</td>
                        <td>${bedel.nombre}</td>
                        <td>${bedel.apellido}</td>
                        <td>${bedel.turno}</td>
                        <td>${bedel.estado ? "Activo" : "Inactivo"}</td>
                    `;
            
                    // Creamos la celda de acciones
                    const actionCell = document.createElement("td");
            
                    // Botón para modificar
                    const modificarBtn = document.createElement("button");
                    modificarBtn.textContent = "Modificar";
                    modificarBtn.className = "modificar-btn";
                    modificarBtn.addEventListener("click", () => {
                        openModalModificar(bedel,row);
                    });
                    actionCell.appendChild(modificarBtn);
            
                    // Botón para eliminar/activar
                    const eliminarBtn = document.createElement("button");
                    eliminarBtn.textContent = bedel.estado ? "Eliminar" : "Activar";
                    eliminarBtn.className = bedel.estado ? "eliminar-btn-active" : "eliminar-btn-eliminado";
                    eliminarBtn.addEventListener("click", () => {
                        openModalEliminar(bedel, eliminarBtn, row);
                    });
                    actionCell.appendChild(eliminarBtn);
            
                    // Añadimos la celda de acciones a la fila
                    row.appendChild(actionCell);
            
                    // Añadimos la fila al cuerpo de la tabla
                    tbody.appendChild(row);
                });
            
                // Añadimos el cuerpo a la tabla
                table.appendChild(tbody);
            
                // Añadimos la tabla al contenedor
                bedelListContainer.appendChild(table);
            })
            .catch(error => {
                console.error("Error:", error);
                mostrarModalConMensajeError("No se pudo cargar la lista de bedeles por un error interno.")
            });
    } catch (error) {
        console.log("Error en la petición: " + error.message);
        mostrarModalConMensajeError("Error al realizar la petición, por favor intente más tarde.")
    }
});

// ----------------------------ELIMINACION DE BEDELS----------------------------

function closeModalEliminar() {
    document.getElementById("modal-eliminar").style.display = "none";
}

function openModalEliminar(bedel, button, bedelItem) {

    const tituloEliminar = document.getElementById('modal-title-eliminar');
    const modalEliminar = document.getElementById('modal-eliminar');

    // Mostrar el modal de eliminar bedel
    const usuarioEliminar = document.getElementById('bedel-eliminar-usuario');
    const nombreEliminar = document.getElementById('bedel-eliminar-nombre');
    const apellidoEliminar = document.getElementById('bedel-eliminar-apellido');
    const turnoEliminar = document.getElementById('bedel-eliminar-turno');

    //Mostrar titulo dependiendo del estado
    tituloEliminar.textContent = `Quiere ${bedel.estado ? "eliminar" : "activar"} el bedel:`;

    // Rellenar la información del bedel en el modal
    usuarioEliminar.textContent = bedel.usuario;
    nombreEliminar.textContent = bedelItem.children[2].textContent;;
    apellidoEliminar.textContent = bedelItem.children[3].textContent;
    turnoEliminar.textContent = bedelItem.children[4].textContent;

    // Mostrar el modal
    modalEliminar.style.display = "block";

    // Al hacer clic en el botón "Guardar" se llama a toggleEstadoBedel
    const saveButton = document.getElementById('save-delete-button');
    saveButton.onclick = function() {
        toggleEstadoBedel(bedel, button, bedelItem);
    };
}

document.getElementById("close-button-eliminar").addEventListener("click", closeModalEliminar);

document.getElementById("cancel-delete-button").addEventListener("click", closeModalEliminar);

// TOGGLE ESTADO (Activar/Eliminar)
function toggleEstadoBedel(bedel, button, bedelItem) {
    //Dependiendo del estado del bedel se llamara a deleteBedel o activarBedel
    const url = bedel.estado 
        ? `/admin/api/deleteBedel/${bedel.id}` 
        : `/admin/api/activarBedel/${bedel.id}`;
    const nuevoEstado = !bedel.estado; //Se invierte el estado original

    //comienza la peticion
    fetch(url, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            //Si hay error muestra mostrarModalConMensajeError y el mensaje
            if (!(response.status === 200 || response.status === 201)) {
                response.json().then(data => {
                    mostrarModalConMensajeError(data.mensaje || "Ocurrio un error desconocido, por favor contactar con soporte."); 
                });
                return;
            }
            return response.json();
        })
        .then(() => {
            
            bedel.estado = nuevoEstado;

            // Actualizamos el estado visual del Bedel
            bedelItem.style.backgroundColor = bedel.estado ? "#7be48c" : "#fcf96f";

            button.textContent = bedel.estado ? "Eliminar" : "Activar";

            if (bedel.estado) {
                button.className = "eliminar-btn-active";  
            } else {
                button.className = "eliminar-btn-eliminado";
            }


            if (bedel.estado) {
                bedelItem.children[5].textContent = "Activo";
            } else {
                bedelItem.children[5].textContent = "Inactivo";
            }

            
            mostrarModalConMensajeExito(`Bedel ${bedel.estado ? "activado" : "eliminado"} correctamente.`);
            closeModalEliminar(); 
        })
        .catch(error => {
            console.error("Error:", error);
            mostrarModalConMensajeError( "Ocurrio un error desconocido, por favor contactar con soporte.");
        });
}

// ----------------------------MODIFICAR DE BEDELS----------------------------

function closeModalModificar() {
    document.getElementById("modal-modificar").style.display = "none";
}

function openModalModificar(bedel,bedelItem) {
    const modal = document.getElementById("modal-modificar");
    //Se agrega un elemento oculto ID para enviar en la peticion y se le asigna el id del bedel
    document.getElementById("bedel-id").value = bedel.id;

    //Se recetea el formulario
    document.getElementById("bedel-modificar-usuario").value = bedel.usuario;
    document.getElementById("bedel-modificar-nombre").value = "";
    document.getElementById("bedel-modificar-apellido").value = "";
    document.getElementById("bedel-modificar-turno").value = "";
    document.getElementById("bedel-modificar-contrasenia").value = "";
    document.getElementById("bedel-modificar-confContrasenia").value = "";

    //se pone los datos actuales en el placeholder, los que se cambien seran enviados en la peticion
    document.getElementById("bedel-modificar-nombre").placeholder = bedelItem.children[2].textContent;
    document.getElementById("bedel-modificar-apellido").placeholder = bedelItem.children[3].textContent;
    document.getElementById("bedel-modificar-turno").placeholder = bedelItem.children[4].textContent;

    modal.style.display = "block";

    const saveButton = document.getElementById('save-modificar-button');
    saveButton.onclick = function() {
        modificarBedel(bedel, bedelItem);
    };
    
}

document.getElementById("close-button-modificar").addEventListener("click", closeModalModificar);

document.getElementById("cancel-modificar-button").addEventListener("click", closeModalModificar);

function modificarBedel(bedel, bedelItem){
    let updatedBedel = {
        id : bedel.id,
        usuario: bedel.usuario,
        //Si no se modifica el atributo se le asigna el valor NULL para que el servidor sepa que atributos fueron cambiados
        nombre: document.getElementById("bedel-modificar-nombre").value || null,
        apellido: document.getElementById("bedel-modificar-apellido").value || null,
        contrasenia : document.getElementById("bedel-modificar-contrasenia").value || null,
        confContrasenia : document.getElementById("bedel-modificar-confContrasenia").value || null,
        turno: document.getElementById("bedel-modificar-turno").value || null
    }

    //Verificaciones antes de la peticion

    if(updatedBedel.nombre === null && 
        updatedBedel.apellido=== null && 
        updatedBedel.contrasenia=== null && 
        updatedBedel.confContrasenia === null && 
        updatedBedel.turno === null){
        mostrarModalConMensajeError("Error: Modifique algun campo para continuar."); 
        return;
    }

    if (updatedBedel.contrasenia !== updatedBedel.confContrasenia) {
        mostrarModalConMensajeError("Error: las contraseñas no coinciden."); 
        return;
    }

    if (updatedBedel.nombre!== null && updatedBedel.nombre.length > 40) {
        mostrarModalConMensajeError("Error: El nombre debe tener menos de 40 caracteres."); 
        return;
    }

    if (updatedBedel.apellido!== null && updatedBedel.apellido.length > 40) {
        mostrarModalConMensajeError("Error: El apellido debe tener menos de 40 caracteres."); 
        return;
    }

    if(updatedBedel.turno!== null && (updatedBedel.turno!== "1" && updatedBedel.turno!== "2")){
        mostrarModalConMensajeError("Error: turno invalido, solo existen dos turnos 1 y 2."); 
        return;
    }


    //Comienza la peticion

    fetch(`/admin/api/updateBedel`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(updatedBedel)
    })
        .then(response => {
            if (!(response.status === 200 || response.status === 201)) {
                response.json().then(data => {
                    mostrarModalConMensajeError(data.mensaje || "Ocurrio un error desconocido, por favor contactar con soporte."); 
                });
                return;
            }
            return response.json();
        })
        .then(() => {
            closeModalModificar();
            mostrarModalConMensajeExito("Bedel actualizado correctamente.");

            if (updatedBedel.nombre) bedelItem.children[2].textContent = updatedBedel.nombre;
            if (updatedBedel.apellido) bedelItem.children[3].textContent = updatedBedel.apellido;
            if (updatedBedel.turno) bedelItem.children[4].textContent = updatedBedel.turno;
        })
        .catch(error => {
            console.error("Error:", error);
            mostrarModalConMensajeError("Ocurrió un error al actualizar el Bedel."); 
        });
};



// ----------------------------MENSAJES, MODALES Y FUNCIONES AUXILIARES----------------------------
function closeModalMensaje() {
    modalResultado.style.display = "none";
}

function mostrarModalConMensajeError(mensaje) {

    botonAceptarResultado.className = "btn-aceptar-modal-resultado-error"

    // Configurar el mensaje
    mensajeModalResultado.textContent = mensaje;

    // Mostrar el modal
    modalResultado.style.display = "block";
}

function mostrarModalConMensajeExito(mensaje) {

    botonAceptarResultado.className = "btn-aceptar-modal-resultado-exito"

    // Configurar el mensaje
    mensajeModalResultado.textContent = mensaje;

    // Mostrar el modal
    modalResultado.style.display = "block";
}

// Cerrar modal resultado al hacer clic en el botón 'Aceptar'

botonAceptarResultado.addEventListener("click", closeModalMensaje);

// Cerrar modal al hacer clic en el botón de cerrar
document.getElementById("close-button-resultado").addEventListener("click", closeModalMensaje);

//Mensaje de filtrado

const mostrarMensajeError = (mensaje) => {
    mensajeError.textContent = mensaje;
};

const limpiarMensajeError = () => {
    mensajeError.textContent = "";
}