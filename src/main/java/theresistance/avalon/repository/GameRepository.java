package theresistance.avalon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import theresistance.avalon.model.GameEntity;

import java.util.List;

public interface GameRepository extends JpaRepository<GameEntity, Long> {

    @Query("SELECT g FROM games g")
    List<GameEntity> getGames();

}
