// ----------------------------Encabezado, Docentes y Materias---------------------------
document.addEventListener('DOMContentLoaded', () => {
  const nombreUsuario = document.getElementById('usuario-nombre');

  //Cargamos el nombre del usuario y Buscamos los docentes y aulas
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

  const docenteSelect = document.getElementById('docente');
  const catedraSelect = document.getElementById('catedra');

  fetch('/bedel/api/getDatosCampus')
    .then(response => response.json())
    .then(data => {
      const { docentes, materias } = data;

      // Llenar el select de docentes
      docentes.forEach(docente => {
        const option = document.createElement('option');
        option.value = docente.id;
        option.textContent = docente.nombre;
        docenteSelect.appendChild(option);
      });

      // Llenar el select de catedras
      materias.forEach(materia => {
        const option = document.createElement('option');
        option.value = materia.id;
        option.textContent = materia.nombre;
        catedraSelect.appendChild(option);
      });
    })
    .catch(error => {
      console.error('Error al obtener el los profesores y las materias:', error);
    });
});

const mensajeError = document.getElementById("mensaje-error-diaReserva");
const mensajeErrorReserva = document.getElementById("mensaje-error-reserva");

const modalSuperposicion = document.getElementById("modal-superposicion");
const modalAulas = document.getElementById("modal-aulas");

//Modal de resultados
const modalResultado = document.getElementById("resultadoModal");
const mensajeModalResultado = document.getElementById("resultadoMensaje");
const botonAceptarResultado = document.getElementById("aceptar-resultado-modal");

// ----------------------------Agregar Dia de Reserva---------------------------

const botonAgregarDia = document.getElementById("btn-agregar-dia");
const botonBuscar = document.getElementById("buscar");
const botonCrearReserva = document.getElementById("crearReserva");
const botonCancelar = document.getElementById("cancelar");

const tabla = document.getElementById("dias-agregados");

botonAgregarDia.addEventListener("click", agregarFechaReserva);

botonBuscar.addEventListener("click", buscarDisponibilidad);

botonCancelar.addEventListener("click", cancelar);

function agregarFechaReserva() {
  const fecha = document.getElementById("fecha-id").value;
  const horaInicio = document.getElementById("hora-inicio-id").value;
  const duracion = document.getElementById('duracion-id').value;

  // VALIDACIONES

  // campo invalido
  if (!fecha || !horaInicio || !duracion) {
      mostrarMensajeError("Todos los campos son necesarios para agregar un día de reserva");
      return;
  }

  // Verificamos que la clase sea en módulos de 30 min
  if (duracion % 30 !== 0) {
      mostrarMensajeError("La duración de la clase debe ser en módulos de 30 minutos, revise la duración");
      return;
  }

  const diaReservaDTO = {
    fecha: fecha,
    horaInicio: horaInicio,
    duracion: parseInt(duracion, 10)
  };

  if (existeSuperposicion(diaReservaDTO)) {
    mostrarMensajeError("Esta intentando agregar un dia que se superpone con otro ya agregado");
    return;
  }

  // CREAR FECHA DIA RESERVA

  limpiarMensajeError();

  const nuevaFila = document.createElement("tr");

  nuevaFila.innerHTML = `
      <td>${fecha}</td>
      <td>${horaInicio}</td>
      <td>${duracion} minutos</td>
      <td></td>
  `;

  // Crear y agregar la celda de acción
  const actionCell = crearActionCellDiasReserva(tabla.rows.length - 1); 
  nuevaFila.appendChild(actionCell);

  // Añadir la nueva fila a la tabla
  tabla.appendChild(nuevaFila);
  limpiarIngresoFecha();
}

function crearActionCellDiasReserva(index) {
  const actionCell = document.createElement("td");

  // Crear un contenedor para los botones
  const buttonContainer = document.createElement("div");
  buttonContainer.className = "button-container";

  // Botón de eliminar
  const eliminarButton = document.createElement("button");
  eliminarButton.textContent = "Eliminar";
  eliminarButton.className = "btn-cancelar-modal";

  // Añadir funcionalidad al botón de eliminar
  eliminarButton.addEventListener("click", (event) => {
      const fila = event.target.closest("tr");
      fila.remove();
  });

  // Botón de disponibilidad (oculto inicialmente)
  const disponibilidadButton = document.createElement("button");
  disponibilidadButton.textContent = "Disponibilidad";
  disponibilidadButton.className = "btn-disponibilidad-tabla";
  disponibilidadButton.id = `disponibilidad-${index}`; 

  // Añadir los botones al contenedor
  buttonContainer.appendChild(eliminarButton);
  buttonContainer.appendChild(disponibilidadButton);

  // Añadir el contenedor a la celda de acción
  actionCell.appendChild(buttonContainer);

  return actionCell;
}

function buscarDisponibilidad() {
  const filas = Array.from(tabla.querySelectorAll("tr")).slice(1); // Saltamos la cabecera

  //VERIFICACIONES
  if (filas.length === 0) {
    mostrarModalConMensajeError("Debe seleccionar al menos una fecha para buscar la disponibilidad");
    return;
  }

  // Obtener los valores de los campos de entrada
  const cantAlumnos = document.getElementById("alumnos-tam").value;
  const idDocente = document.getElementById("docente").value;
  const nombreDocente = document.getElementById("docente").selectedOptions[0].textContent;
  const idAsignatura = document.getElementById("catedra").value;
  const nombreAsignatura = document.getElementById("catedra").selectedOptions[0].textContent;

  // Verificar si algún campo es null o está vacío
  if (!cantAlumnos || !idDocente || !idAsignatura || !nombreDocente || !nombreAsignatura) {
      mostrarMensajeErrorReserva("Todos los campos son obligatorios para buscar la disponibilidad de la reserva");
      return;
  }

  //^VERIFICACIONES^
  
  limpiarMensajeErrorReserva();

  limpiarMensajeError();

  empezarTransaccion();

  // Crear el objeto reserva
  const reserva = {
    idDocente: parseInt(idDocente, 10),
    idAsignatura: parseInt(idAsignatura, 10),
    nombreDocente: nombreDocente,
    nombreAsignatura: nombreAsignatura,
    cantAlumnos: parseInt(cantAlumnos, 10),
    tipo: 1 // Esporadica
  };


  // Crear lista de objetos diaReservaDTO
  const diasReservaDTO = filas.map(fila => {
      const columnas = fila.querySelectorAll("td");
      return {
          fechaReserva: columnas[0].textContent.trim(),
          horaInicio: columnas[1].textContent.trim() + ":00", // Aseguramos el formato HH:MM:SS
          duracion: parseInt(columnas[2].textContent.trim(), 10) // Convertir a número
      };
  });

  // Crear objeto requeriminetoDisponibilidadDTO
  const requeriminetoDisponibilidadDTO = {
    cantidadAlumnos: reserva.cantAlumnos,
    tipoReserva: reserva.tipo,
    tipoAula: parseInt(document.getElementById("turno").value,10),
    diasReserva: diasReservaDTO
  };


  // Enviar al backend con fetch
  try {
      fetch("/bedel/api/getAula/esporadica", {
          method: "POST",
          headers: {
              "Content-Type": "application/json"
          },
          body: JSON.stringify(requeriminetoDisponibilidadDTO)
      })
          .then(response => {
              if (!(response.status === 200 || response.status === 201)) {
                  response.json().then(data => {
                    mostrarModalConMensajeError((data.mensaje));
                    cancelarTransaccion();
                  });
                  return;
              }
              return response.json();
          })
          .then(data => {
              if (!data.hasOwnProperty("resultado")) {
                mostrarModalConMensajeError("ERROR: interno del servidor, falta el resultado de la operacion, se aborta la transaccion");
                cancelarTransaccion();
              }

              botonCrearReserva.addEventListener("click", () => crearReserva(reserva));

              const disponibilidadList = data.resultado;
              // Procesar la respuesta del servidor
              disponibilidadList.forEach((disponibilidad, index) => {
                  const disponibilidadButton = document.getElementById(`disponibilidad-${index}`);
                  const fila = filas[index];

                  if (disponibilidad.superposicion) {
                      disponibilidadButton.addEventListener("click", () => mostrarSuperposicion(disponibilidad.reserva));
                      const celdaAula = fila.querySelector("td:nth-child(4)");
                      celdaAula.textContent = "Superposicion";
                      celdaAula.value = -1;
                      celdaAula.setAttribute("superpuesto", "true");
                  } else {
                      disponibilidadButton.addEventListener("click", () => seleccionarAula(disponibilidad.aulasCandidatas,fila));
                  }
                  disponibilidadButton.style.display = "inline-block";
              });

          })
          .catch(error => {
              console.log(`Error: ${error.message}`);
              cancelarTransaccion();
          });
  } catch (error) {
      console.log("Error en la petición: " + error.message);
      cancelarTransaccion();
  }
}


function mostrarSuperposicion(reserva) {
  // Mostrar el modal de superposición

  // Setear los atributos del modal con los datos de la reserva
  document.getElementById("reserva-id-modal-sup").textContent = reserva.id;
  document.getElementById("asignatura-id-modal-sup").textContent = reserva.idAsignatura;
  document.getElementById("asignatura-modal-sup").textContent = reserva.nombreAsignatura;
  document.getElementById("docente-id-modal-sup").textContent = reserva.idDocente;
  document.getElementById("docente-modal-sup").textContent = reserva.nombreDocente;
  document.getElementById("tipo-asignatura-modal-sup").textContent = reserva.tipo ? "Periodica" : "Esporadica";
  document.getElementById("bedel-modal-sup").textContent = reserva.idBedel;

  modalSuperposicion.style.display = "block";
}

document.getElementById("save-superposicion-button").addEventListener("click",closeModalSuperposicion);

document.getElementById("close-button-superposicion").addEventListener("click", closeModalSuperposicion);

function seleccionarAula(aulasCandidatas, row) {
  const aulaInfo = document.getElementById("aula-info");
  // Limpiar contenido previo
  aulaInfo.innerHTML = ""; 

  aulasCandidatas.forEach(aula => {
      // Crear un div para cada aula candidata
      const aulaDiv = document.createElement("div");
      aulaDiv.className = "aula-candidata";

      // Llenar el div con la información del aula
      aulaDiv.innerHTML = `
          <p><strong>ID:</strong> ${aula.id}</p>
          <p><strong>Máximo de Alumnos:</strong> ${aula.maximoAlumnos}</p>
          <p><strong>Piso:</strong> ${aula.piso}</p>
          <p><strong>Tipo de Pizarrón:</strong> ${aula.tipoPizarron}</p>
      `;

      // Crear y agregar la celda de acción
      const actionCell = crearActionCellAulaCandidata(aula, row);
      aulaDiv.appendChild(actionCell);

      aulaInfo.appendChild(aulaDiv);
  });

  // Mostrar el modal de aulas
  modalAulas.style.display = "block";
}

document.getElementById("cancel-aula-button").addEventListener("click",closeModalAulas);

document.getElementById("close-button-aula").addEventListener("click",closeModalAulas);


function crearActionCellAulaCandidata(aula, row) {
  const buttonContainer = document.createElement("div");
  buttonContainer.className = "button-container";

  // Botón de seleccionar aula
  const seleccionarButton = document.createElement("button");
  seleccionarButton.textContent = "Seleccionar";
  seleccionarButton.className = "btn-seleccionar-aula";

  // Añadir funcionalidad al botón de seleccionar
  seleccionarButton.addEventListener("click", () => {
      const celdaAula = row.querySelector("td:nth-child(4)");
      celdaAula.textContent = aula.id;
      celdaAula.value = aula.id;
      celdaAula.setAttribute("superpuesto", "false");

      // Cerrar el modal
      modalAulas.style.display = "none";
  });

  // Añadir el botón al contenedor
  buttonContainer.appendChild(seleccionarButton);

  return buttonContainer;
}


// ----------------------------Crear Reserva---------------------------

function empezarTransaccion() {
  // Ocultar los botones de agregar y buscar
  botonBuscar.style.display = "none";
  botonAgregarDia.style.display = "none";

  botonCancelar.removeEventListener("click", cancelar);
  botonCancelar.addEventListener("click",cancelarTransaccion);

  // Bloquear los campos de entrada
  const camposNoHabilitados = document.querySelectorAll("#campos-reserva input, #campos-reserva select, #fechas-reservas-sub-container input");
  camposNoHabilitados.forEach(campo => {
    campo.disabled = true;
  });

  // Mostrar el botón de crear reserva
  botonCrearReserva.style.display = "inline-block";
}

function cancelarTransaccion() {
  botonCrearReserva.style.display = "none";
  botonCrearReserva.removeEventListener("click",crearReserva);

  botonBuscar.style.display = "inline-block";
  botonAgregarDia.style.display = "inline-block";

  botonCancelar.removeEventListener("click",cancelarTransaccion);
  botonCancelar.addEventListener("click", cancelar);

  // Habilitar los campos de entrada
  const camposNoHabilitados = document.querySelectorAll("#campos-reserva input, #campos-reserva select, #fechas-reservas-sub-container input");
  camposNoHabilitados.forEach(campo => {
    campo.disabled = false;
  });

}

function crearReserva(reserva) {
  // VERIFICACION

  // Saltamos la cabecera
  const filas = Array.from(tabla.querySelectorAll("tr")).slice(1); 

  const diasReservaDTO = [];

  for (const fila of filas) {
    const celdaAula = fila.querySelector("td:nth-child(4)");
    const superpuesto = celdaAula.getAttribute("superpuesto");

    if (superpuesto === "true") {
      mostrarModalConMensajeError("Hay reservas con superposición. Por favor, resuélvalas antes de continuar.");
      return;
    }

    if (!celdaAula.value) {
      mostrarModalConMensajeError("Hay reservas sin aula asignada. Por favor, asigne un aula antes de continuar.");
      return;
    }

    const columnas = fila.querySelectorAll("td");
    const diaReservaDTO = {
        fechaReserva: columnas[0].textContent.trim(),
        horaInicio: columnas[1].textContent.trim() + ":00",
        duracion: parseInt(columnas[2].textContent.trim(), 10), 
        idAula: parseInt(celdaAula.value, 10) 
    };

    diasReservaDTO.push(diaReservaDTO);
  }
  //^VERIFICACION^

  reserva.listaDiasReservaDTO = diasReservaDTO;

  // Enviar la reserva al backend
  fetch("/bedel/api/reserva/registrar", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(reserva)
  })
    .then(response => {
      if (!(response.status === 200 || response.status === 201)) {
        response.json().then(data => {
          mostrarModalConMensajeError(data.mensaje || "Ocurrio un error desconocido, por favor contactar con soporte.");
          cancelarTransaccion(); 
      });
      return;
      }
      return response.json();
    })
    .then(data => {
      mostrarModalConMensajeExito(data.mensaje);
      cancelarTransaccion();
      limpiarReserva();
    })
    .catch(error => {
      mostrarModalConMensajeError(`Error: ${error.message}`);
      cancelarTransaccion();
      limpiarReserva();
    });
}

//FUNCIONES AUXILIARES

function existeSuperposicion(diaReserva) {
  const filas = tabla.getElementsByTagName("tr");

  for (let i = 1; i < filas.length; i++) { 
      const columnas = filas[i].getElementsByTagName("td");
      const fecha = columnas[0].textContent.trim();
      const horaInicio = columnas[1].textContent.trim();
      const duracion = parseInt(columnas[2].textContent.trim(), 10);

      if (fecha === diaReserva.fecha) {
          const [horaInicioTabla, minutosInicioTabla] = horaInicio.split(":").map(Number);
          const [horaInicioReserva, minutosInicioReserva] = diaReserva.horaInicio.split(":").map(Number);

          const inicioTabla = horaInicioTabla * 60 + minutosInicioTabla;
          const inicioReserva = horaInicioReserva * 60 + minutosInicioReserva;

          const finTabla = inicioTabla + duracion;
          const finReserva = inicioReserva + diaReserva.duracion;

          if ((inicioReserva >= inicioTabla && inicioReserva < finTabla) || 
              (finReserva > inicioTabla && finReserva <= finTabla) ||
              (inicioTabla >= inicioReserva && inicioTabla < finReserva)) {
              return true;
          }
      }
  }
  return false;
}

function limpiarIngresoFecha(){
  document.getElementById("fecha-id").value = "";  
  document.getElementById("hora-inicio-id").value = "";
  document.getElementById('duracion-id').value = "";
};

const mostrarMensajeError = (mensaje) => {
  mensajeError.textContent = mensaje;
};

const limpiarMensajeError = () => {
  mensajeError.textContent = "";
};

const mostrarMensajeErrorReserva = (mensaje) => {
  mensajeErrorReserva.textContent = mensaje;
};

const limpiarMensajeErrorReserva = () => {
  mensajeErrorReserva.textContent = "";
};

function limpiarReserva() {
  // Vaciar la tabla de reservas
  if (tabla) {
      const filas = tabla.getElementsByTagName("tr");
      const numFilas = filas.length;
      for (let i = numFilas - 1; i > 0; i--) {
          tabla.deleteRow(i);
      }
  }

  // Vaciar los campos de entrada
  document.getElementById("alumnos-tam").value = "";
  document.getElementById("docente").selectedIndex = 0;
  document.getElementById("catedra").selectedIndex = 0;
  document.getElementById("turno").selectedIndex = 0;

  // Vaciar los campos de fecha y hora
  document.getElementById("fecha-id").value = "";
  document.getElementById("hora-inicio-id").value = "";
  document.getElementById("duracion-id").value = "";

  limpiarMensajeErrorReserva();
  limpiarMensajeError();
};


function closeModalSuperposicion() {
  modalSuperposicion.style.display = "none";
};

function closeModalAulas() {
  modalAulas.style.display = "none";
};

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

botonAceptarResultado.addEventListener("click", closeModalMensaje);

document.getElementById("close-button-resultado").addEventListener("click", closeModalMensaje);

function cancelar() {
  // Redirigir a la URL especificada
  window.location.href = '/bedel/reserva/crear';
}
