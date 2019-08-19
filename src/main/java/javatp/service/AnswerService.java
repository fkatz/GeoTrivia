
package javatp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javatp.exception.IncompleteObjectException;
import java.util.List;

import javatp.domain.Answer;
import javatp.domain.POI;
import javatp.domain.Question;
import javatp.repository.AnswerRepository;

@Service
public class AnswerService {
    @Autowired
    AnswerRepository answerRepository;

    public Answer getAnswer(Long id) {
        return answerRepository.getOne(id);
    }
    
    public boolean answerExistsByID(Long poiId, Long questionId, Long id) {
        return answerRepository.isInPOIAndQuestion(new POI(poiId), new Question(id), new Answer(id));
    }

    public Answer createAnswer(Answer answer) {
        if (answer.getId() != 0 && answer.getContent() != "" && answer.getIsCorrect() != null && answer.getQuestion() != null) {
            return answerRepository.save(answer);
        } else
            throw new IncompleteObjectException("All properties are required");
    }

    public boolean deleteAnswer(Answer answer) {
        try {
            answerRepository.delete(answer);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    public Answer updateAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public List<Answer> createAnswers(List<Answer> answers) {
        return answerRepository.saveAll(answers);
    }
}