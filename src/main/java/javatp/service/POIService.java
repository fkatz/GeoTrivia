package javatp.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import javatp.domain.POI;
import javatp.repository.POIRepository;
import org.springframework.stereotype.Service;

@Service
public class POIService {
    @Autowired
    POIRepository poiRepository;

    public POI getPOI(Long id) {
        return poiRepository.getOne(id);
    }

    public POI createPOI(POI poi) {
        return poiRepository.save(poi);
    }

    public boolean deletePOI(POI poi) {
        try {
            poiRepository.delete(poi);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    public POI updatePOI(POI poi) {
        return poiRepository.save(poi);
    }

    public List<POI> getAllPOIs() {
        return poiRepository.findAll();
    }

    public List<POI> createPOIs(List<POI> pois) {
        return poiRepository.saveAll(pois);
    }
}