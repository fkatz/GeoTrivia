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

import javatp.domain.Answer;
import javatp.service.AnswerService;


@RestController
@RequestMapping(value = "/api/answers")
public class AnswerController{
    @Autowired
    private AnswerService answerService;
    
	@PostMapping(value = "/answer")
	public ResponseEntity<Object> createAnswer(@RequestBody Answer answer) {
		Answer newAnswer = answerService.createAnswer(answer);
		return ResponseEntity.ok(newAnswer);
    }
    
    @GetMapping(value = "/answer/{id}")
	public ResponseEntity<Object> getAnswer(@PathVariable("id") long id) {
		Answer answer = answerService.getAnswer(id);
		return ResponseEntity.ok(answer);
	}

    @PutMapping(value = "/answer/{id}")
	public ResponseEntity<Object> updateAnswer(@PathVariable("id") long id, @RequestBody Answer answer) {
		if(!answerService.AnswerExistsByID(id)) throw new EntityNotFoundException();
		answer.setId(id);
		Answer updatedAnswer = answerService.updateAnswer(answer);
		return ResponseEntity.ok(updatedAnswer);
	}

	@DeleteMapping(value = "/answer/{id}")
	public ResponseEntity<Object> deleteAnswer(@PathVariable("id") long id) {
		Answer answer = answerService.getAnswer(id);
		answerService.deleteAnswer(answer);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "")
	public ResponseEntity<Object> getAnswers() {
        List<Answer> answers = answerService.getAllAnswers();
		return ResponseEntity.ok(answers);
	}

	@PostMapping(value = "")
	public ResponseEntity<Object> createAnswers(@RequestBody List<Answer> answers) {
        List<Answer> newAnswers = answerService.createAnswers(answers);
		return ResponseEntity.ok(newAnswers);
	}
}
