package theresistance.avalon.model;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;
import theresistance.avalon.repository.UserRepository;

import java.util.List;

@Entity(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long game_id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private UserEntity creator_id;

    private String gameName;

    private boolean voting_type;
    private int max_players;

    public GameEntity() { }

    public Long getGame_id() {
        return game_id;
    }

    public void setGame_id(Long game_id) {
        this.game_id = game_id;
    }

    public UserEntity getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(UserEntity creator_id) {
        this.creator_id = creator_id;
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
}
