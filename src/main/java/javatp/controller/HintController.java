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
import javatp.domain.POI;
import javatp.service.HintService;
import javatp.service.POIService;

@RestController
@RequestMapping(value = "/api/pois/{poiId}/hints")
public class HintController {
    @Autowired
    private HintService hintService;
    @Autowired
    private POIService poiService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createHint(@PathVariable("poiId") long poiId, @RequestBody Hint hint) {
        if (!poiService.POIExistsByID(poiId))
            throw new EntityNotFoundException();
        hint.setPOI(new POI(poiId));
        Hint newHint = hintService.createHint(hint);
        return ResponseEntity.ok(newHint);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getHint(@PathVariable("poiId") long poiId, @PathVariable("id") long id) {
        if (!hintService.hintExistsByID(poiId, id))
            throw new EntityNotFoundException();
        return ResponseEntity.ok(hintService.getHint(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateHint(@PathVariable("poiId") long poiId, @PathVariable("id") long id,
            @RequestBody Hint hint) {
        if (!hintService.hintExistsByID(poiId, id))
            throw new EntityNotFoundException();
        hint.setId(id);
        hint.setPOI(new POI(poiId));
        Hint updatedHint = hintService.updateHint(hint);
        return ResponseEntity.ok(updatedHint);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteHint(@PathVariable("poiId") long poiId, @PathVariable("id") long id) {
        if (!hintService.hintExistsByID(poiId, id))
            throw new EntityNotFoundException();
        Hint hint = new Hint(id);
        hintService.deleteHint(hint);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getHints(@PathVariable("poiId") long poiId) {
        List<Hint> hints = poiService.getPOI(poiId).getHints();
        return ResponseEntity.ok(hints);
    }

    @PostMapping(value = "/batch")
    public ResponseEntity<Object> createHints(@PathVariable("poiId") long poiId, @RequestBody List<Hint> hints) {
        POI poi = new POI(poiId);
        for (Hint hint : hints) {
            hint.setPOI(poi);
        }
        List<Hint> newHints = hintService.createHints(hints);
        return ResponseEntity.ok(newHints);
    }
}