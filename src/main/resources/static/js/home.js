document.addEventListener("DOMContentLoaded", function() {
    // Load the current user and initialize room count
    loadCurrentUser();
    loadGames(); // Fetch and display games on page load
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
                    window.location.href = "../login.html"
                }
            })
            .catch(error => {
                console.error("Error fetching current user:", error);
            });
    }

    // Show the overlay when the "Create Room" button is clicked
    document.getElementById('createRoomBtn').addEventListener('click', function() {
        document.getElementById('gameOverlay').style.display = 'flex'; // Show overlay
    });

    // Close the overlay when the "Cancel" button is clicked
    document.getElementById('cancelBtn').addEventListener('click', function() {
        document.getElementById('gameOverlay').style.display = 'none'; // Hide overlay
    });

    // Function to add a game to the database
    function addToBase(gameName, players, openVotes) {
        // Fetch the creator ID from the /currentUser API
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
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(gameData)
                    })
                        .then(response => response.json())
                        .then(data => {
                            console.log('Game added successfully:', data);
                            // Reload games after adding a new one
                            loadGames();
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

    // Function to load games from the database
    function loadGames() {
        fetch('/loadGames', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => response.json())
            .then(data => {
                const gameContainer = document.getElementById('roomsContainer');
                gameContainer.innerHTML = ''; // Clear existing content

                if (!data.success) {
                    console.error('Failed to load games:', data.message);
                    gameContainer.innerHTML = '<p>Error loading games</p>';
                    return;
                }

                const games = data.games;

                if (!Array.isArray(games) || games.length === 0) {
                    gameContainer.innerHTML = '<p>No games available</p>';
                    return;
                }

                games.forEach(game => {
                    // Create a div for each game with a 'game-box' class for styling
                    const gameDiv = document.createElement('div');
                    gameDiv.classList.add('game-box');

                    // Display Game ID in small font in the top-left corner
                    const gameIdDiv = document.createElement('div');
                    gameIdDiv.classList.add('game-id');
                    gameIdDiv.textContent = `Game ID: ${game.game_id}`;

                    // Display Game Name in the center (bolder, slightly smaller)
                    const gameNameDiv = document.createElement('div');
                    gameNameDiv.classList.add('game-name');
                    gameNameDiv.textContent = game.gameName;

                    // Display other information (creator, max players, voting type) aligned
                    const gameInfoDiv = document.createElement('div');
                    gameInfoDiv.classList.add('game-info');

                    // Creator ID (smaller font)
                    fetch(`/userById?user_id=${game.creator_id}`)
                        .then(response => response.json())
                        .then(user => {
                            // Display Creator Username
                            const creatorUsernameDiv = document.createElement('div');
                            creatorUsernameDiv.classList.add('game-creator');
                            creatorUsernameDiv.textContent = `Creator: ${user.username}`;
                            gameInfoDiv.appendChild(creatorUsernameDiv);
                        })
                        .catch(error => {
                            console.error('Error fetching creator username:', error);
                        });

                    // Max Players
                    const maxPlayersDiv = document.createElement('div');
                    maxPlayersDiv.textContent = `Max Players: ${game.max_players}`;

                    // Voting Type
                    const votingTypeDiv = document.createElement('div');
                    votingTypeDiv.textContent = `Voting Type: ${game.voting_type ? 'Open' : 'Closed'}`;

                    // Status (smaller font, cozy styling)
                    const statusDiv = document.createElement('div');
                    statusDiv.classList.add('game-status');
                    statusDiv.textContent = `Status: ${game.status}`;

                    // Append all information to the game info div
                    gameInfoDiv.appendChild(maxPlayersDiv);
                    gameInfoDiv.appendChild(votingTypeDiv);
                    gameInfoDiv.appendChild(statusDiv);

                    // Create the Join button with the #4CAF50 color
                    const joinButton = document.createElement('button');
                    joinButton.classList.add('join-btn');
                    joinButton.textContent = 'Join Game';

                    // Append elements to the game div
                    gameDiv.appendChild(gameIdDiv);
                    gameDiv.appendChild(gameNameDiv);
                    gameDiv.appendChild(gameInfoDiv);
                    gameDiv.appendChild(joinButton);

                    // Append the game div to the main container
                    gameContainer.appendChild(gameDiv);
                });
            })
            .catch(error => {
                console.error('Error fetching games:', error);
                document.getElementById('roomsContainer').innerHTML = '<p>Error fetching games</p>';
            });
    }


    // Handle form submission for creating a game
    document.getElementById('createGameForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        // Get form values
        const gameName = document.getElementById('gameName').value;
        const players = document.getElementById('players').value;
        const openVotes = document.getElementById('openVotes').checked;

        // Add the game to the database
        addToBase(gameName, players, openVotes);

        // Close the overlay after creating the room
        document.getElementById('gameOverlay').style.display = 'none';

        // Reset the form fields
        document.getElementById('createGameForm').reset();
    });
});
