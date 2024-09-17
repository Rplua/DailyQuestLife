document.addEventListener('DOMContentLoaded', function() {
    const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));

    // Redirigir al login si no hay usuario logueado
    if (!loggedInUser) {
        window.location.href = 'login.html';
        return;
    }

    // Mostrar información del usuario en la página
    mostrarInformacionUsuario(loggedInUser);

    // Cargar misiones del usuario
    cargarMisionesUsuario(loggedInUser.id);

    // Cargar lista de amigos excluyendo al usuario actual
    cargarAmigosExcluyendoUsuario(loggedInUser.id);

    // Obtener y mostrar todos los objetos
    cargarObjetos(loggedInUser.id);

    // Añadir eventos
    agregarEventos(loggedInUser);
});

// Función para mostrar la información del usuario en la página
function mostrarInformacionUsuario(usuario) {
    document.querySelector('.name').innerText = `${usuario.name} ${usuario.apellido}`;
    document.querySelector('.nivel span').innerText = usuario.nivel;
    document.getElementById('profileImage').src = usuario.img;
    //para el futuro
    document.getElementById('followers').innerText = usuario.followrs || '0';
    document.getElementById('following').innerText = usuario.following || '0';
    document.getElementById('friends').innerText = usuario.friends || '0';
    document.getElementById('userName').innerText = usuario.usuario;
}

// Función para cargar las misiones del usuario
function cargarMisionesUsuario(userId) {
    fetch(`http://localhost:8080/user/${userId}/misiones`)
        .then(response => response.json())
        .then(misiones => {
            const misionContainer = document.getElementById('mision');
            misionContainer.innerHTML = ''; // Limpiar misiones anteriores
            if(misiones.length == 0){
                const mensaje = document.createElement('p');
                mensaje.textContent = 'Aún no tienes misiones clica al botón para que genere aleatorias.😁';
                misionContainer.appendChild(mensaje);
            }
            misiones.forEach(mision => {
                const misionElement = crearMisionElemento(mision);
                misionContainer.appendChild(misionElement);
            });
        })
        .catch(error => console.error('Error loading missions:', error));
}

// Función para crear un elemento de misión
function crearMisionElemento(mision) {
    const misionElement = document.createElement('div');
    misionElement.className = 'mision-item';

    // Crear botón de eliminar misión
    const deleteButton = document.createElement('button');
    deleteButton.className = 'delete-mision';
    deleteButton.innerText = 'X';
    deleteButton.addEventListener('click', function() {
        misionElement.remove();
    });

    misionElement.appendChild(deleteButton);

    // Añadir nombre de la misión
    const misionText = document.createElement('span');
    misionText.innerText = mision.nombre;
    misionElement.appendChild(misionText);

    return misionElement;
}

// Función para cargar amigos excluyendo al usuario actual
function cargarAmigosExcluyendoUsuario(userId) {
    fetch(`http://localhost:8080/user/exclude/${userId}`)
        .then(response => response.json())
        .then(users => {
            const amigosDiv = document.getElementById('amigos');
            amigosDiv.innerHTML = ''; // Limpiar contenido actual

            users.forEach(user => {
                const friendDiv = crearAmigoElemento(user);
                amigosDiv.appendChild(friendDiv);
            });
        })
        .catch(error => console.error('Error loading friends:', error));
}

// Función para crear un elemento de amigo
function crearAmigoElemento(user) {
    const friendDiv = document.createElement('div');
    friendDiv.className = 'friend row align-items-center mb-4';
    friendDiv.innerHTML = `
        <div class="friend-img col-3">
            <img src="${user.img || 'default-profile.png'}" alt="${user.name}" class="img-fluid rounded-circle">
        </div>
        <div class="friend-info col-9">
            <h3 class="mb-1">${user.name}</h3>
            <h4 class="text-muted">Nivel: ${user.nivel}</h4>
            <p class="text-muted">Email: ${user.email}</p>
        </div>
           <div class="friend-stats col-3 text-right">
            <p id="followers">Seguidores: ${user.followrs || '0'}</p>
            <p id="following">Siguiendo: ${user.following || '0'}</p>
            <p id="friends">Amigos: ${user.friends || '0'}</p>
        </div>
    `;
    return friendDiv;
}

// Función para cargar todos los objetos
function cargarObjetos(userId) {
    fetch(`http://localhost:8080/user/${userId}/available-objectos`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta de la red: ' + response.statusText);
            }
            return response.text(); // Usa text() para obtener el contenido crudo
        })
        .then(text => {
            console.log('Respuesta cruda:', text); // Imprime el contenido crudo
            try {
                const objects = JSON.parse(text); // Intenta analizar el JSON
                if (!Array.isArray(objects)) {
                    throw new Error('La respuesta no es un array');
                }
                const objectoDiv = document.getElementById('objecto');
                objectoDiv.innerHTML = ''; // Limpiar contenido actual
                objectoDiv.className = 'object-grid'; // Clase para cuadrícula

                if (objects.length === 0) {
                    objectoDiv.innerHTML = '<p>No hay objetos disponibles.</p>';
                } else {
                    objects.forEach(object => {
                        const objectDiv = crearObjetoElemento(object);
                        objectoDiv.appendChild(objectDiv);
                    });
                }
            } catch (error) {
                console.error('Error al analizar el JSON:', error);
                document.getElementById('objecto').innerHTML = '<p>Error al cargar los objetos.</p>';
            }
        })
        .catch(error => console.error('Error loading objects:', error));
}


// Función para crear un elemento de objeto todos
function crearObjetoElemento(object) {
    const objectDiv = document.createElement('div');
    objectDiv.className = 'object-item';
    objectDiv.innerHTML = `
     <div class="col-3 object-img">
            <h3>${object.tipo}</h3>
            <img src="${object.img || 'default-object.png'}" alt="${object.tipo}">
        </div>
        <div class="col-9 object-description">
            <p>${object.objectodes || 'Descripción no disponible.'}</p>
        </div>
    `;
    return objectDiv;
}
// Función para crear un elemento de objeto del usuari0
function crearObjetoElementoUser(object) {
    const objectDiv = document.createElement('div');
    objectDiv.className = 'object-item-user'; 
    objectDiv.innerHTML = `
     <div class="col-3 object-img1">
            <h3>${object.tipo}</h3>
            <img src="${object.img || 'default-object.png'}" alt="${object.tipo}">
        </div>
        <div class="col-9 object-description">
            <p>${object.objectodes || 'Descripción no disponible.'}</p>
        </div>
    `;
    return objectDiv;
}
async function cargarTusObjectos(userId) {
    try {
        // Verificar que el userId esté definido
        if (!userId) {
            throw new Error('ID de usuario no definido');
        }

        // Llama a la API para obtener los objetos del usuario
        const response = await fetch(`http://localhost:8080/user/${userId}/objectos`);

        // Verificar si la respuesta es exitosa
        if (!response.ok) {
            const errorText = await response.text(); // Obtener texto del error
            throw new Error(`Error al obtener los objetos del usuario: ${response.status} ${response.statusText} ${errorText}`);
        }

        const objetos = await response.json(); // Convierte la respuesta en JSON

        const contenedorObjetos = document.getElementById('tus-objectos');
        contenedorObjetos.innerHTML = ''; // Limpia cualquier contenido previo

        // Si el usuario tiene objetos, crea los elementos correspondientes
        if (objetos && objetos.length > 0) {
            objetos.forEach(objecto => {
                const objectElement = crearObjetoElementoUser(objecto);
                contenedorObjetos.appendChild(objectElement);
            });
        } else {
            // Mensaje cuando no hay objetos
            contenedorObjetos.innerHTML = '<p>Aún no tienes objetos. ¡Haz más misiones para desbloquearlos!</p>';
        }
    } catch (error) {
        console.error('Error al cargar los objetos:', error.message); // Mostrar mensaje de error detallado
    }
}

// Función para agregar eventos
function agregarEventos(loggedInUser) {
    // Evento para cerrar sesión
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', function() {
            localStorage.removeItem('loggedInUser');
            window.location.href = 'index.html';
        });
    }

    // Evento para eliminar cuenta
    const deleteAccountButton = document.getElementById('deleteAccountButton');
    if (deleteAccountButton) {
        deleteAccountButton.addEventListener('click', function() {
            const confirmDelete = confirm('¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.');
            if (confirmDelete) {
                eliminarCuenta(loggedInUser.id);
            }
        });
    }

    // Evento para generar misiones aleatorias
    const generateMissionsButton = document.getElementById('generateMissions');
    if (generateMissionsButton) {
        generateMissionsButton.addEventListener('click', function() {
            generarMisionesAleatorias();
        });
    }

    // Evento para actualizar datos del usuario
    document.getElementById('userUpdateForm').addEventListener('submit', function(event) {
        event.preventDefault();
        actualizarUsuario(loggedInUser);
    });
    document.getElementById('tusobjectos-tab').addEventListener('click', function() {
        const userId = loggedInUser.id; // Aquí debes cambiar esto para obtener el ID real del usuario
        cargarTusObjectos(userId);
    });
   
}

// Función para generar misiones aleatorias
function generarMisionesAleatorias() {
    fetch('http://localhost:8080/mision/random')
        .then(response => response.json())
        .then(data => {
            if (!Array.isArray(data)) throw new Error('El formato de datos es incorrecto');

            const misionContainer = document.getElementById('mision');
            misionContainer.innerHTML = ''; // Limpiar misiones anteriores

            data.forEach(mision => {
                const misionElement = crearMisionElemento(mision);
                misionContainer.appendChild(misionElement);
            });
        })
        .catch(error => console.error('Error loading missions:', error));
}

// Función para actualizar datos del usuario
function actualizarUsuario(loggedInUser) {
    const userId = loggedInUser.id;
    const currentUserName = document.getElementById('UserName').value;
    const newUserName = document.getElementById('newUserName').value;
    const currentPassword = document.getElementById('currentPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const newEmail = document.getElementById('newEmail').value;
   

    if (newPassword !== confirmPassword) {
        alert('La nueva contraseña y la confirmación no coinciden.');
        return;
    }

    const updatedUser = {
        id: userId,
        name: loggedInUser.name,
        apellido: loggedInUser.apellido,
        usuario : newUserName || currentUserName, 
        password: newPassword || currentPassword,
        email: newEmail,
        img : loggedInUser.img,
        followrs : loggedInUser.followrs,
        following: loggedInUser.following,
        friends : loggedInUser.friends
    };

    fetch(`http://localhost:8080/user/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedUser)
    })
    .then(response => response.json())
    .then(data => {
        alert('Usuario actualizado exitosamente.');
        localStorage.clear();

        window.location.href = 'login.html';
    })
    .catch(error => {
        console.error('Error al actualizar usuario:', error);
        alert('Hubo un problema al actualizar el usuario.');
    });
}

// Función para eliminar la cuenta del usuario
function eliminarCuenta(userId) {
    fetch(`http://localhost:8080/user/${userId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            alert('Cuenta eliminada exitosamente.');
            localStorage.removeItem('loggedInUser'); // Eliminar la sesión del usuario
            window.location.href = 'index.html'; // Redirigir al inicio
        } else {
            throw new Error('Error al eliminar la cuenta.');
        }
    })
    .catch(error => {
        console.error('Error al eliminar la cuenta:', error);
        alert('Hubo un problema al eliminar la cuenta.');
    });
}