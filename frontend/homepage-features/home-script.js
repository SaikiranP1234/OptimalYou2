// Display the username on the home page
document.addEventListener('DOMContentLoaded', () => {
    const usernameDisplay = document.getElementById('username-display');
    const userIcon = document.getElementById('user-icon');
    const username = localStorage.getItem('username');

    if (username) {
        userIcon.textContent = username.charAt(0).toUpperCase();
        usernameDisplay.textContent = username;
    } else {
        // If no username is found, redirect to login
        window.location.href = '../index.html';
    }
});
