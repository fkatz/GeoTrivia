package javatp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javatp.domain.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
