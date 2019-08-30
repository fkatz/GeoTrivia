package javatp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javatp.domain.Game;
import javatp.domain.User;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("SELECT COUNT(game)>0 FROM Game game WHERE game.player=?1 and game=?2")
    boolean isInUser(User user, Game game);

    @Query("SELECT game FROM Game game WHERE game.player=?1 and game.finishTime = null")
    Game findLastGameForUser(User user);
}