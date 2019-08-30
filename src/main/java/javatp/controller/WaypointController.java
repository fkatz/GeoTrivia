package javatp.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javatp.domain.Waypoint;
import javatp.service.GameService;

@RestController
@RequestMapping(value = "/api/users/{userId}/games/{gameId}/waypoints")
public class WaypointController {
    @Autowired
    private GameService gameService;

    @GetMapping(value = "")
    public ResponseEntity<Object> getWaypoints(@PathVariable("userId") long userId,@PathVariable("gameId") long gameId) {
        if (!gameService.gameExistsByID(userId, gameId))
        throw new EntityNotFoundException();
        Waypoint waypoint = gameService.getGame(gameId).getRootWaypoint();
        return ResponseEntity.ok(waypoint);
    }

    @GetMapping(value = "/last")
    public ResponseEntity<Object> getUnresolvedWaypoints(@PathVariable("userId") long userId,@PathVariable("gameId") long gameId) {
        if (!gameService.gameExistsByID(userId, gameId))
        throw new EntityNotFoundException();
        Waypoint waypoint = gameService.getGame(gameId).getRootWaypoint().getLastUnresolved();
        return ResponseEntity.ok(waypoint);
    }
}