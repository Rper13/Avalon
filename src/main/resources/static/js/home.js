document.addEventListener('DOMContentLoaded', function() {
    let roomCount = 0; // To track how many rooms have been created

    // Function to create a new room
    function createRoom() {
        roomCount++; // Increment room count

        // Create a new room div
        const roomDiv = document.createElement('div');
        roomDiv.classList.add('room');

        // Create the room name
        const roomName = document.createElement('div');
        roomName.classList.add('room-name');
        roomName.textContent = `Room ${roomCount}`; // Dynamic name based on count

        // Create a join button
        const joinButton = document.createElement('button');
        joinButton.classList.add('join-room');
        joinButton.textContent = 'Join Game';

        // Append room name and button to the room div
        roomDiv.appendChild(roomName);
        roomDiv.appendChild(joinButton);

        // Append the new room div to the available rooms container
        document.getElementById('roomsContainer').appendChild(roomDiv);
    }

    // Event listener for the Create Room button
    document.getElementById('createRoomBtn').addEventListener('click', createRoom);
});
