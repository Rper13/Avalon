package theresistance.avalon.dto;

public class GameDto {

    private Long game_id;

    private String gameName;

    private boolean voting_type;
    private int max_players;
    private String status;

    private Long creator_id;

    public GameDto(Long game_id, String gameName, boolean voting_type, int max_players, int status, Long creator_id) {
        this.game_id = game_id;
        this.gameName = gameName;
        this.voting_type = voting_type;
        this.max_players = max_players;
        this.status = "[Something is Wrong]";
        switch (status) {
            case 1 -> this.status = "Not Started";
            case 2 -> this.status = "In Progress";
            case 3 -> this.status = "Ended";
        }

        this.creator_id = creator_id;
    }

    public Long getGame_id() {
        return game_id;
    }

    public void setGame_id(Long game_id) {
        this.game_id = game_id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean isVoting_type() {
        return voting_type;
    }

    public void setVoting_type(boolean voting_type) {
        this.voting_type = voting_type;
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }
    public Long getCreator_id() {
        return creator_id;
    }

    public void setCreator_username(Long creator_id) {
        this.creator_id = creator_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
