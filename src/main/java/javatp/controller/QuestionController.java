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
import javatp.service.QuestionService;


@RestController
@RequestMapping(value = "/api/questions")
public class QuestionController{
    @Autowired
    private QuestionService questionService;
    
	@PostMapping(value = "/question")
	public ResponseEntity<Object> createQuestion(@RequestBody Question question) {
		Question newQuestion = questionService.createQuestion(question);
		return ResponseEntity.ok(newQuestion);
    }
    
    @GetMapping(value = "/question/{id}")
	public ResponseEntity<Object> getQuestion(@PathVariable("id") long id) {
		Question question = questionService.getQuestion(id);
		return ResponseEntity.ok(question);
	}

    @PutMapping(value = "/question/{id}")
	public ResponseEntity<Object> updatePOI(@PathVariable("id") long id, @RequestBody Question question) {
		if(!questionService.questionExistsByID(id)) throw new EntityNotFoundException();
		question.setId(id);
		Question updatedQuestion = questionService.updateQuestion(question);
		return ResponseEntity.ok(updatedQuestion);
	}

	@DeleteMapping(value = "/question/{id}")
	public ResponseEntity<Object> deleteQuestion(@PathVariable("id") long id) {
		Question question = questionService.getQuestion(id);
		questionService.deleteQuestion(question);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "")
	public ResponseEntity<Object> getQuestions() {
        List<Question> questions = questionService.getAllQuestions();
		return ResponseEntity.ok(questions);
	}

	@PostMapping(value = "")
	public ResponseEntity<Object> createQuestions(@RequestBody List<Question> questions) {
        List<Question> newQuestions = questionService.createQuestions(questions);
		return ResponseEntity.ok(newQuestions);
	}
}
