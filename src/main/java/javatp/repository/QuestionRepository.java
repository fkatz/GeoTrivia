package javatp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javatp.domain.POI;
import javatp.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT COUNT(question)>0 FROM Question question WHERE question.poi=?1 and question=?2")
    boolean isInPOI(POI poi, Question question);

    @Query("SELECT COUNT(question)>0 FROM Question question WHERE question.content=?1 and question.POI=?2")
    boolean existsByContentAndPOI(String content, POI poi);
}
