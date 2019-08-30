package javatp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javatp.domain.Waypoint;

@Repository
public interface WaypointRepository extends JpaRepository<Waypoint, Long> {
}