package javatp.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javatp.domain.Question;
import javatp.domain.POI;
import javatp.service.POIService;
import javatp.service.QuestionService;

@RestController
@RequestMapping(value="/api/pois/{poiId}/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private POIService poiService;
    
    @PostMapping(value="")
    public ResponseEntity<Object> createQuestion(@PathVariable("poiId") long poiId,@RequestBody Question question){
        POI poi = poiService.getPOI(poiId);
        question.setPOI(poi);
        Question newQuestion = questionService.createQuestion(question);
        return ResponseEntity.ok(newQuestion);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Object> getQuestion(@PathVariable("poiId") long poiId,@PathVariable("id") long id){
        if(!questionService.questionExistsByID(poiId, id))
        throw new EntityNotFoundException();
        return ResponseEntity.ok(questionService.getQuestion(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateQuestion(@PathVariable("poiId") long poiId,@PathVariable("id") long id,@RequestBody Question question){
        if(!questionService.questionExistsByID(poiId, id))
        throw new EntityNotFoundException();
        question.setId(id);
        question.setPOI(new POI(poiId));
        Question updatedQuestion = questionService.updateQuestion(question);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable("poiId") long poiId,@PathVariable("id") long id){
        if(!questionService.questionExistsByID(poiId, id))
        throw new EntityNotFoundException();
        Question question = new Question(id);
        questionService.deleteQuestion(question);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "")
	public ResponseEntity<Object> getQuestions(@PathVariable("poiId") long poiId) {
        List<Question> questions = poiService.getPOI(poiId).getQuestions();
		return ResponseEntity.ok(questions);
    }
    
    @PostMapping(value = "/batch")
	public ResponseEntity<Object> createQuestions(@PathVariable("poiId") long poiId,@RequestBody List<Question> questions) {
        POI poi = new POI(poiId);
        for (Question question : questions) {
            question.setPOI(poi);
        }
        List<Question> newQuestions = questionService.createQuestions(questions);
		return ResponseEntity.ok(newQuestions);
	}
}