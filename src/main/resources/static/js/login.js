document.getElementById('loginForm').addEventListener('submit', function(e) {
    e.preventDefault(); // Prevent form from submitting normally

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const formData = new FormData();
    formData.append('username', username);
    formData.append('password', password);

    // Send POST request using Fetch API
    fetch('/login_request', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json()) // Expect JSON response
        .then(data => {
            if (data.success) {
                // Redirect to home page on successful login
                window.location.href = '/home';
            } else {
                // Display error message on failure
                const errorMessage = document.getElementById('errorMessage');
                errorMessage.style.display = 'block';
                errorMessage.querySelector('.error-text').textContent = data.message;
            }
        })
        .catch(error => {
            console.error('Error:', error);
            const errorMessage = document.getElementById('errorMessage');
            errorMessage.style.display = 'block';
            errorMessage.querySelector('.error-text').textContent = 'Something went wrong. Please try again later.';
        });
});
