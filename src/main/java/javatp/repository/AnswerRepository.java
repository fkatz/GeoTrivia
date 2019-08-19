package javatp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javatp.domain.Answer;
import javatp.domain.POI;
import javatp.domain.Question;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT COUNT(answer)>0 FROM Answer answer WHERE answer.question.poi=?1 and answer.question=?2 and answer=?3")
    boolean isInPOIAndQuestion(POI poi, Question question, Answer answer);
}