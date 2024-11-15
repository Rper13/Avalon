package theresistance.avalon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import theresistance.avalon.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM users u where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT count(u) > 0 from users u where u.username = :username")
    boolean userExists(@Param("username") String username);

}
