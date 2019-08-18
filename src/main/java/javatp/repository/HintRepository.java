package javatp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javatp.domain.Hint;
import javatp.domain.POI;

@Repository
public interface HintRepository extends JpaRepository<Hint, Long> {
    @Query("SELECT COUNT(hint)>0 FROM Hint hint WHERE hint.poi=?1 and hint =?2")
    boolean isInPOI(POI poi, Hint hint);

    @Query("SELECT hint FROM Hint hint WHERE hint.poi=?1")
    List<Hint> FindByPoi(POI poi);
}