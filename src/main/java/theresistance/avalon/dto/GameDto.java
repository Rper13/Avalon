package theresistance.avalon.dto;

public class GameDto {

    private Long game_id;

    private String gameName;

    private boolean voting_type;
    private int max_players;
    private int status;

    private Long creator_id;

    public GameDto(Long game_id, boolean voting_type, int max_players, int status, Long creator_id) {
        this.game_id = game_id;
        this.voting_type = voting_type;
        this.max_players = max_players;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getCreator_id() {
        return creator_id;
    }

    public void setCreator_username(Long creator_id) {
        this.creator_id = creator_id;
    }
}
