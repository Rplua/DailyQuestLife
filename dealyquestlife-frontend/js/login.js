document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar la recarga de la página
    
    const userName = document.getElementById('userName').value;
    const password = document.getElementById('password').value;

    fetch('http://localhost:8080/user/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            usuario : userName,
            password: password
        })
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Invalid credentials');
        }
    })
    .then(user => {
        localStorage.setItem('loggedInUser', JSON.stringify(user));
        window.location.href = 'userpage.html';
    })
    .catch(error => {
        console.error('Error:', error);
        alert(error);
    });
});
