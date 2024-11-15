// Register the form submission handler
document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault();  // Prevent default form submission

    // Get form values
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var repeatPassword = document.getElementById('repeatPassword').value;

    // Clear previous error messages
    document.getElementById('error-message').innerHTML = '';

    // Simple validation for matching passwords
    if (password !== repeatPassword) {
        document.getElementById('error-message').innerHTML = 'Passwords do not match!';
        return;
    }

    // Prepare data for the AJAX request
    var data = {
        username: username,
        password: password
    };

    // Create a new XMLHttpRequest
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/register_request?username=' + username + '&password=' + password, true);

    // Set up a function to handle the response
    xhr.onload = function() {
        if (xhr.status === 200) {
            // Parse JSON response
            var response = JSON.parse(xhr.responseText);

            // Check if registration was successful
            if (response.success) {
                // Redirect to the home page or dashboard
                window.location.href = '/home';
            } else {
                // Show error message
                document.getElementById('error-message').innerHTML = response.message;
            }
        } else {
            // Handle server errors or unexpected issues
            document.getElementById('error-message').innerHTML = 'Something went wrong. Please try again later.';
        }
    };

    // Handle network errors
    xhr.onerror = function() {
        document.getElementById('error-message').innerHTML = 'Network error. Please try again.';
    };

    // Send the request
    xhr.send();
});
