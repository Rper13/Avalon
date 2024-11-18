document.addEventListener("DOMContentLoaded", function() {
    // Load the current user and initialize room count
    loadCurrentUser();
    let roomCount = 0; // To track how many rooms have been created
    let user_id = 0;
    // Function to load the current username
    function loadCurrentUser() {
        fetch('/currentUser')
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    const usernameElement = document.getElementById("nav-username");
                    if (usernameElement) {
                        usernameElement.textContent = data.username; // Display the username
                    } else {
                        console.error("Element with ID 'nav-username' not found.");
                    }
                } else {
                    console.error(data.message);
                }
            })
            .catch(error => {
                console.error("Error fetching current user:", error);
            });
    }

    // Function to create a new room with the game details
    function createRoom(gameName, players, openVotes) {
        roomCount++; // Increment room count
        const gameId = `ID-${roomCount}`; // Generate a unique game ID (you can replace this with a more sophisticated ID generator)

        // Create the room div element
        const roomDiv = document.createElement('div');
        roomDiv.classList.add('room');

        // Room name element
        const roomName = document.createElement('div');
        roomName.classList.add('room-name');
        roomName.textContent = gameName || `Room ${roomCount}`; // Use the game name or default to Room #X

        // Game ID (with transparency)
        const roomId = document.createElement('div');
        roomId.classList.add('room-id');
        roomId.textContent = gameId;

        // Vote status element
        const voteStatus = document.createElement('div');
        voteStatus.classList.add('vote-status');
        voteStatus.textContent = openVotes ? 'Votes Open' : 'Votes Closed';

        // Players and room size element
        const playersStatus = document.createElement('div');
        playersStatus.classList.add('players-status');
        playersStatus.textContent = `${players} Players`;

        // Join button element
        const joinButton = document.createElement('button');
        joinButton.classList.add('join-room');
        joinButton.textContent = 'Join Game';

        // Append the elements to the room div
        roomDiv.appendChild(roomId);
        roomDiv.appendChild(roomName);
        roomDiv.appendChild(voteStatus);
        roomDiv.appendChild(playersStatus);
        roomDiv.appendChild(joinButton);

        // Append the new room div to the available rooms container
        document.getElementById('roomsContainer').appendChild(roomDiv);
    }

    // Show the overlay when the "Create Room" button is clicked
    document.getElementById('createRoomBtn').addEventListener('click', function() {
        document.getElementById('gameOverlay').style.display = 'flex'; // Show overlay
    });

    // Close the overlay when the "Cancel" button is clicked
    document.getElementById('cancelBtn').addEventListener('click', function() {
        document.getElementById('gameOverlay').style.display = 'none'; // Hide overlay
    });

    function addToBase(gameName, players, openVotes) {
        // First, fetch the creator ID from the /currentUser API
        fetch('/currentUser')
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    const creatorId = data.user_id;

                    // Prepare the data to send in the request body
                    const gameData = {
                        gameName: gameName,
                        creatorId: creatorId,
                        max_players: players,
                        voting_type: openVotes
                    };

                    // Send a POST request to the /addGame endpoint
                    fetch('/addGame', {
                        method: 'POST', // HTTP method
                        headers: {
                            'Content-Type': 'application/json' // Specify content type as JSON
                        },
                        body: JSON.stringify(gameData) // Convert the data to JSON format
                    })
                        .then(response => response.json())
                        .then(data => {
                            console.log('Game added successfully:', data);
                        })
                        .catch(error => {
                            console.error('Error adding game:', error);
                        });
                } else {
                    console.error("Failed to fetch current user:", data.message);
                }
            })
            .catch(error => {
                console.error("Error fetching creator ID:", error);
            });
    }

    // Handle form submission for creating a game
    document.getElementById('createGameForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        // Get form values
        const gameName = document.getElementById('gameName').value;
        const players = document.getElementById('players').value;
        const openVotes = document.getElementById('openVotes').checked;
        
        addToBase(gameName, players, openVotes);
        
        // Create the room with the provided details
        createRoom(gameName, players, openVotes);

        // Close the overlay after creating the room
        document.getElementById('gameOverlay').style.display = 'none';

        // Reset the form fields
        document.getElementById('createGameForm').reset();
    });
});
