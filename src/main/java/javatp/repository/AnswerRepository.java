package javatp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javatp.domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
