package javatp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javatp.exception.EntityContentRepeatedException;
import javatp.exception.IncompleteObjectException;
import java.util.List;

import javatp.domain.POI;
import javatp.domain.Question;
import javatp.repository.QuestionRepository;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public Question getQuestion(Long id) {
        return questionRepository.getOne(id);
    }

    public boolean questionExistsByID(Long poiId, Long id) {
        return questionRepository.isInPOI(new POI(poiId), new Question(id));
    }

    public Question createQuestion(Question question) {
        if (!question.getContent().equals("") && question.getPOI() != null && question.getContent() != null) {
            if (!questionRepository.existsByContentAndPOI(question.getContent(), question.getPOI())) {
                return questionRepository.save(question);
            } else {
                throw new EntityContentRepeatedException("Question Repeated");
            }
        } else
            throw new IncompleteObjectException("All properties are required");
    }

    public boolean deleteQuestion(Question question) {
        try {
            questionRepository.delete(question);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> createQuestions(List<Question> questions) {
        return questionRepository.saveAll(questions);
    }
}
