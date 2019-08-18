package javatp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javatp.domain.POI;

@Repository
public interface POIRepository extends JpaRepository<POI, Long> {

}