window.onload = async function() {
    const studentId = 1;  // Assuming you want to fetch profile for student with ID 1
    const profileSection = document.getElementById('profile');
    const errorSection = document.getElementById('error');

    try {
        // Fetch student profile data
        const profileResponse = await fetch(`/student/${studentId}/profile`);

        if (!profileResponse.ok) {
            throw new Error('Profile not found');
        }

        const student = await profileResponse.json();

        // Display student data
        document.getElementById('student-name').textContent = student.name;
        document.getElementById('student-age').textContent = `Age: ${student.age}`;
        document.getElementById('student-university').textContent = `University: ${student.university.name}`;

        // Fetch student image data
        const imageResponse = await fetch(`/student/${studentId}/image`);

        if (imageResponse.ok) {
            const imageBlob = await imageResponse.blob();
            const imageUrl = URL.createObjectURL(imageBlob);
            document.getElementById('student-image').src = imageUrl;
        } else {
            // If image is not found, show a default placeholder
            document.getElementById('student-image').src = 'images/default-profile.png';
        }

        // Show profile and hide error
        profileSection.style.display = 'block';
        errorSection.style.display = 'none';

    } catch (error) {
        // Show error message if profile or image is not found
        profileSection.style.display = 'none';
        errorSection.textContent = `Error: ${error.message}`;
        errorSection.style.display = 'block';
    }
};
