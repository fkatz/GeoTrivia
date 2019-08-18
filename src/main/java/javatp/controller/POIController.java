package javatp.controller;

import javatp.domain.POI;
import javatp.service.POIService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(value = "/api/pois")
public class POIController{
    @Autowired
    private POIService poiService;
    
	@PostMapping(value = "/poi")
	public ResponseEntity<Object> createPOI(@RequestBody POI poi) {
		POI newPOI = poiService.createPOI(poi);
		return ResponseEntity.ok(newPOI);
    }
    
    @GetMapping(value = "/poi/{id}")
	public ResponseEntity<Object> getPOI(@PathVariable("id") long id) {
		POI poi = poiService.getPOI(id);
		return ResponseEntity.ok(poi);
	}

    @PutMapping(value = "/poi/{id}")
	public ResponseEntity<Object> updatePOI(@PathVariable("id") long id, @RequestBody POI poi) {
		if(!poiService.POIExistsByID(id)) throw new EntityNotFoundException();
		poi.setId(id);
		POI updatedPoi = poiService.updatePOI(poi);
		return ResponseEntity.ok(updatedPoi);
	}

	@DeleteMapping(value = "/poi/{id}")
	public ResponseEntity<Object> deletePOI(@PathVariable("id") long id) {
		POI poi = poiService.getPOI(id);
		poiService.deletePOI(poi);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "")
	public ResponseEntity<Object> getPOIs() {
        List<POI> pois = poiService.getAllPOIs();
		return ResponseEntity.ok(pois);
	}

	@PostMapping(value = "")
	public ResponseEntity<Object> createPOIs(@RequestBody List<POI> pois) {
        List<POI> newPOIs = poiService.createPOIs(pois);
		return ResponseEntity.ok(newPOIs);
	}
}