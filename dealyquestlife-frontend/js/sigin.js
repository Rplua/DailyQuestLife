// Manejo del botón de registro
document.getElementById('signup-button').addEventListener('click', function() {
    // Recoger los valores del formulario
    const name = document.getElementById('new-name').value.trim();
    const apellido = document.getElementById('new-surname').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('new-password').value.trim();
    const img = document.getElementById('img').value.trim();
    const newUser = document.getElementById('new-username').value.trim()

    // Verificación básica de los campos
    if (!name || !apellido || !email || !password ) {
        alert('Por favor, completa todos los campos.');
        return;
    }

    // Realizar la petición POST para crear el usuario
    fetch('http://localhost:8080/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            usuario: newUser,
            name: name,
            apellido: apellido,  
            email: email,
            password: password,
            img: img
        })
    })
    .then(response => {
        if (!response.ok) {
        
            return response.json().then(data => {
                throw new Error(data.message || 'Error en el registro. Por favor, inténtalo de nuevo.');
            });
        }
        return response.json();
    })
    .then(user => {
        // Guardar el usuario registrado en localStorage
        localStorage.setItem('loggedInUser', JSON.stringify(user));
        // Redirigir a la página del usuario
        window.location.href = 'userpage.html';
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error.message || 'Ha ocurrido un error durante el registro.');
    });
});
