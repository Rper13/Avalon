package theresistance.avalon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import theresistance.avalon.model.GameEntity;

public interface GameRepository extends JpaRepository<GameEntity, Long> {


}
