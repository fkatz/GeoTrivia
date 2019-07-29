package javatp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javatp.entities.POI;

@Repository
public interface POIRepository extends JpaRepository<POI, Long> {
}