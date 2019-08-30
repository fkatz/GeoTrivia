package javatp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javatp.domain.Answer;
import javatp.domain.Game;
import javatp.domain.Hint;
import javatp.domain.POI;
import javatp.domain.Question;
import javatp.domain.QuestionAnswerResponse;
import javatp.domain.User;
import javatp.domain.Waypoint;
import javatp.domain.WaypointGuessResponse;
import javatp.exception.IncompleteObjectException;
import javatp.exception.NoHintsLeftException;
import javatp.exception.NoQuestionsInPOIException;
import javatp.service.GameService;
import javatp.service.UserService;

@RestController
@RequestMapping(value = "/api/game")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createGame(@RequestBody POI poi) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (poi.getLat() == null || poi.getLng() == null)
            throw new IncompleteObjectException("Latitude and longitude required");
        Game game = new Game();
        game.setPlayer(user);
        Game newGame = gameService.createAndInitializeGame(game, poi);
        return ResponseEntity.ok(newGame);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getGame() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameService.findLastGameForUser(user);
        return ResponseEntity.ok(game);
    }

    @GetMapping(value = "/waypoint")
    public ResponseEntity<Object> getWaypoint() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameService.findLastGameForUser(user);
        Waypoint waypoint = game.getRootWaypoint().getLastUnresolved();
        for(Waypoint nextWaypoint:waypoint.getNext()){
            nextWaypoint.setIsCorrrect(null);
        }
        return ResponseEntity.ok(waypoint);
    }

    @GetMapping(value = "/waypoint/history")
    public ResponseEntity<Object> getWaypointHistory() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameService.findLastGameForUser(user);
        Waypoint waypoint = game.getRootWaypoint();
        return ResponseEntity.ok(waypoint);
    }

    @PostMapping(value = "/waypoint/guess")
    public ResponseEntity<Object> getWaypointHistory(@RequestBody POI poi) {
        final double CORRECT_DISTANCE_THRESHOLD = 0.005;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameService.findLastGameForUser(user);
        Waypoint correct = game.getRootWaypoint().getLastCorrect();
        if (correct.getPOI().getLocation().distance(poi.getLocation()) < CORRECT_DISTANCE_THRESHOLD) {
            this.gameService.setNextWaypoint(correct);
            return ResponseEntity.ok(new WaypointGuessResponse(true, correct));
        }
        return ResponseEntity.ok(new WaypointGuessResponse(false, null));
    }

    @GetMapping(value = "/hints")
    public ResponseEntity<Object> getHints() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameService.findLastGameForUser(user);
        Waypoint waypoint = game.getRootWaypoint().getLastUnresolved();
        return ResponseEntity.ok(waypoint.getGivenHints());
    }

    @GetMapping(value = "/hints/new")
    public ResponseEntity<Object> getNewHint() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameService.findLastGameForUser(user);
        Waypoint waypoint = game.getRootWaypoint().getLastUnresolved();
        if (game.getHintsLeft() > 0) {
            Hint nextHint = gameService.setNextHint(waypoint);
            if (nextHint == null) {
                throw new NoHintsLeftException("No hints left in POI");
            }
            game.setHintsLeft(game.getHintsLeft() - 1);
            gameService.updateGame(game);
            return ResponseEntity.ok(nextHint);
        }
        throw new NoHintsLeftException("No hints left in POI");
    }

    @GetMapping(value = "/question")
    public ResponseEntity<Object> getQuestion() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameService.findLastGameForUser(user);
        Waypoint waypoint = game.getRootWaypoint().getLastUnresolved();
        Question question = waypoint.getQuestion();
        if (question == null) {
            question = gameService.setQuestion(waypoint);
            if (question == null) {
                throw new NoQuestionsInPOIException();
            }
        }
        for (Answer answer : question.getAnswers()) {
            answer.setIsCorect(null);
        }
        return ResponseEntity.ok(waypoint.getQuestion());
    }

    @PostMapping(value = "/question/answer")
    public ResponseEntity<Object> getWaypointHistory(@RequestBody Answer answer) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameService.findLastGameForUser(user);
        Waypoint waypoint = game.getRootWaypoint().getLastUnresolved();
        Question question = waypoint.getQuestion();
        if (question.getCorrectAnswer().equals(answer)) {
            Hint hint = gameService.setNextHint(waypoint);
            return ResponseEntity.ok(new QuestionAnswerResponse(true, hint));
        }
        return ResponseEntity.ok(new QuestionAnswerResponse(false, null));
    }

    @GetMapping(value = "/history")
    public ResponseEntity<Object> getGames(@PathVariable("userId") long userId) {
        List<Game> games = userService.getUser(userId).getGames();
        return ResponseEntity.ok(games);
    }
}