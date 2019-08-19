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
import javatp.domain.Answer;
import javatp.service.AnswerService;
import javatp.service.QuestionService;

@RestController
@RequestMapping(value = "/api/pois/{poiId}/questions/{questionId}/answers")
public class AnswerController {
	@Autowired
	private AnswerService answerService;
	@Autowired
	private QuestionService questionService;

	@PostMapping(value = "")
	public ResponseEntity<Object> createAnswer(@PathVariable("poiId") long poiId,
			@PathVariable("questionId") long questionId, @RequestBody Answer answer) {
		if (!questionService.questionExistsByID(poiId, questionId))
			throw new EntityNotFoundException();
		answer.setQuestion(new Question(questionId));
		Answer newAnswer = answerService.createAnswer(answer);
		return ResponseEntity.ok(newAnswer);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getAnswer(@PathVariable("poiId") long poiId,
			@PathVariable("questionId") long questionId, @PathVariable("id") long id) {
		if (!answerService.answerExistsByID(poiId, questionId, id))
			throw new EntityNotFoundException();
		return ResponseEntity.ok(answerService.getAnswer(id));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> updateAnswer(@PathVariable("poiId") long poiId, @PathVariable("id") long id,
			@PathVariable("questionId") long questionId, @RequestBody Answer answer) {
		if (!answerService.answerExistsByID(poiId, questionId, id))
			throw new EntityNotFoundException();
		answer.setId(id);
		answer.setQuestion(new Question(questionId));
		Answer updatedAnswer = answerService.updateAnswer(answer);
		return ResponseEntity.ok(updatedAnswer);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> deleteAnswer(@PathVariable("poiId") long poiId,
			@PathVariable("questionId") long questionId, @PathVariable("id") long id) {
		if (!answerService.answerExistsByID(poiId, questionId, id))
			throw new EntityNotFoundException();
		Answer answer = new Answer(id);
		answerService.deleteAnswer(answer);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "")
	public ResponseEntity<Object> getAnswers(@PathVariable("poiId") long poiId,
			@PathVariable("questionId") long questionId) {
		if (!questionService.questionExistsByID(poiId, questionId))
			throw new EntityNotFoundException();
		List<Answer> answers = questionService.getQuestion(questionId).getAnswers();
		return ResponseEntity.ok(answers);
	}

	@PostMapping(value = "/batch")
	public ResponseEntity<Object> createAnswers(@PathVariable("poiId") long poiId,
			@PathVariable("questionId") long questionId, @RequestBody List<Answer> answers) {
		if (!questionService.questionExistsByID(poiId, questionId))
			throw new EntityNotFoundException();
		Question question = new Question(questionId);
		for (Answer answer : answers) {
			answer.setQuestion(question);
		}
		List<Answer> newAnswers = answerService.createAnswers(answers);
		return ResponseEntity.ok(newAnswers);
	}
}