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

import javatp.domain.Hint;
import javatp.service.HintService;

@RestController
@RequestMapping(value="/api/hints")
public class HintController {
    @Autowired
    private HintService hintService;
    
    @PostMapping(value="/hint")
    public ResponseEntity<Object> createHint(@RequestBody Hint hint){
        Hint newHint = hintService.createHint(hint);
        return ResponseEntity.ok(newHint);
    }

    @GetMapping(value="/hint/{id}")
    public ResponseEntity<Object> getHint(@PathVariable("id") long id){
        Hint hint = hintService.getHint(id);
        return ResponseEntity.ok(hint);
    }

    @PutMapping(value = "/hint/{id}")
    public ResponseEntity<Object> updateHint(@PathVariable("id") long id,@RequestBody Hint hint){
        if(!hintService.hintExistsByID(id)) throw new EntityNotFoundException();
        hintService.getHint(id);
        hint.setId(id);
        Hint updatedHint = hintService.updateHint(hint);
        return ResponseEntity.ok(updatedHint);
    }

    @DeleteMapping(value="/hint/{id}")
    public ResponseEntity<Object> deleteHint(@PathVariable("id") long id){
        Hint hint = hintService.getHint(id);
        hintService.deleteHint(hint);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "")
	public ResponseEntity<Object> getHints() {
        List<Hint> hints = hintService.getAllHints();
		return ResponseEntity.ok(hints);
    }
    
    @PostMapping(value = "")
	public ResponseEntity<Object> createHints(@RequestBody List<Hint> hints) {
        List<Hint> newHints = hintService.createHints(hints);
		return ResponseEntity.ok(newHints);
	}
}