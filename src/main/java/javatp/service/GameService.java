package javatp.service;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javatp.domain.Game;
import javatp.domain.Hint;
import javatp.domain.POI;
import javatp.domain.Question;
import javatp.domain.User;
import javatp.domain.Waypoint;
import javatp.exception.IncompleteObjectException;
import javatp.repository.GameRepository;
import javatp.repository.WaypointRepository;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    WaypointRepository waypointRepository;
    @Autowired
    POIService poiService;

    final private int NODES = 3;

    public Game getGame(Long id) {
        return gameRepository.getOne(id);
    }

    public boolean gameExistsByID(Long userId, Long id) {
        return gameRepository.isInUser(new User(userId), new Game(id));
    }

    public Game findLastGameForUser(User user){
        return gameRepository.findLastGameForUser(user);
    }

    public Game createAndInitializeGame(Game game, POI poi) {
        if (game.getPlayer() != null) {
            Waypoint root = new Waypoint(true);
            root.setPOI(poi);
            List<Waypoint> children = randomizeWaypoints(root, this.NODES);
            root.setNext(children);
            root.setPOI(null);
            game.setRootWaypoint(root);
            return gameRepository.save(game);
        } else
            throw new IncompleteObjectException("Player is required");
    }

    public Waypoint setNextWaypoint(Waypoint waypoint){
        Waypoint lastCorrect = waypoint.getLastCorrect();
        lastCorrect.setNext(this.randomizeWaypoints(lastCorrect, this.NODES));
        return lastCorrect;
    }

    public List<Waypoint> randomizeWaypoints(Waypoint root, int nodes) {
        List<POI> closest;
        if (root.getNext()== null) {
            closest = poiService.findClosest(root.getPOI(), 10);
        } else{
            List<POI> used = root.getUsedPOIs();
            closest = poiService.findClosestExcluding(root.getLastCorrect().getPOI(), used, 10);
        }
        ArrayList<Waypoint> children = new ArrayList<Waypoint>();
        for (int i = 0; i < nodes; i++) {
            int index = (int) Math.floor(Math.random() * closest.size());
            POI selectedPOI = closest.get(index);
            Waypoint waypoint = new Waypoint();
            waypoint.setPOI(selectedPOI);
            waypoint.setIsCorrrect(false);
            children.add(waypoint);
            closest.remove(index);
        }
        int correctIndex = (int) Math.floor(Math.random() * nodes);
        children.get(correctIndex).setIsCorrrect(true);
        return children;
    }

    public Hint setNextHint(Waypoint waypoint){
        Hint nextHint = randomizeHint(waypoint.getLastCorrect().getPOI(), waypoint.getGivenHints());
        if(nextHint != null){
            waypoint.getGivenHints().add(nextHint);
            waypointRepository.save(waypoint);
            return nextHint;
        }
        return null;
    }

    public Hint randomizeHint(POI poi, List<Hint> usedHints){
        List<Hint> hints = poi.getHints();
        List<Hint> notUsedHints = new ArrayList<Hint>();
        for (Hint hint : hints) {
            if(!usedHints.contains(hint)){
                notUsedHints.add(hint);
            }
        }
        if(notUsedHints.size()>0){
        int index = (int) Math.floor(Math.random()*notUsedHints.size());
        return notUsedHints.get(index);
        }
        return null;
    }

    public Question setQuestion(Waypoint waypoint){
        Question question = randomizeQuestion(waypoint.getPOI());
        if(question != null){
            waypoint.setQuestion(question);
            waypointRepository.save(waypoint);
            return question;
        }
        return null;
    }

    public Question randomizeQuestion(POI poi){
        List<Question> questions = poi.getQuestions();
        if(questions.size()>0){
        int index = (int) Math.floor(Math.random()*questions.size());
        return questions.get(index);
        }
        return null;
    }

    public void deleteGame(Game game) {
        try {
            gameRepository.delete(game);
        } catch (Exception exception) {
            throw new EntityNotFoundException();
        }
    }

    public Game updateGame(Game game) {
        return gameRepository.save(game);
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Game> createGames(List<Game> games) {
        return gameRepository.saveAll(games);
    }
}