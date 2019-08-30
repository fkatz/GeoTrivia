package javatp.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javatp.domain.Waypoint;
import javatp.repository.WaypointRepository;

@Service
public class WaypointService {
    @Autowired
    WaypointRepository waypointRepository;

    public Waypoint getWaypoint(Long id) {
        return waypointRepository.getOne(id);
    }

    public Waypoint createWaypoint(Waypoint waypoint) {
        return waypointRepository.save(waypoint);
    }

    public void deleteWaypoint(Waypoint waypoint) {
        try {
            waypointRepository.delete(waypoint);
        } catch (Exception exception) {
            throw new EntityNotFoundException();
        }
    }

    public Waypoint updateWaypoint(Waypoint waypoint) {
        return waypointRepository.save(waypoint);
    }

    public List<Waypoint> getAllWaypoints() {
        return waypointRepository.findAll();
    }

    public List<Waypoint> createWaypoints(List<Waypoint> waypoints) {
        return waypointRepository.saveAll(waypoints);
    }
}