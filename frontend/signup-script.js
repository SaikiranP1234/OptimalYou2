const signupForm = document.getElementById('signUpForm');
const errorMessage = document.getElementById('error-message');

signupForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    try {
        const response = await fetch('http://localhost:8080/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username:username, password:password }),
        });

        if (response.ok) {
            const token = await response.text();
            localStorage.setItem('username', username.value);
            localStorage.setItem('jwtToken', token);
            alert('SignUp successful!');
            window.location.href = 'login.html';
        } else {
            throw new Error('Username already exists');
        }
    } catch (error) {
        errorMessage.textContent = error.message;
        errorMessage.style.display = 'block';
    }
});
