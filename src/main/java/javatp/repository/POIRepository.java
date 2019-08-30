package javatp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javatp.domain.POI;

@Repository
public interface POIRepository extends JpaRepository<POI, Long> {
    @Query(value = "SELECT * FROM public.pois " 
            + "WHERE id NOT IN :ids "
            + "ORDER BY location <->CONCAT('SRID=4326;POINT(', :lng, ' ', :lat,')')\\:\\:geometry "
            + "LIMIT :limit", nativeQuery = true)
    List<POI> findClosestExcluding(@Param("lat")double lat, @Param("lng")double lng, @Param("limit")int limit, @Param("ids") List<Long> ids);
    @Query(value = "SELECT * FROM public.pois " 
    + "ORDER BY location <->CONCAT('SRID=4326;POINT(', :lng, ' ', :lat,')')\\:\\:geometry "
    + "LIMIT :limit", nativeQuery = true)
List<POI> findClosest(@Param("lat")double lat, @Param("lng")double lng, @Param("limit")int limit);
}