package javatp.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javatp.domain.Hint;
import javatp.domain.POI;
import javatp.exception.IncompleteObjectException;
import javatp.repository.HintRepository;

@Service
public class HintService {
    @Autowired
    HintRepository hintRepository;

    public Hint getHint(Long id) { 
        return hintRepository.getOne(id);
    }

    public boolean hintExistsByID(Long poiId, Long id) {
        return hintRepository.isInPOI(new POI(poiId), new Hint(id));
    }

    public Hint createHint(Hint hint) {
        if (hint.getDescription() != "" && hint.getPoi() != null) {
            return hintRepository.save(hint);
        } else
            throw new IncompleteObjectException("All properties are required");
    }

    public void deleteHint(Hint hint) {
        try {
            hintRepository.delete(hint);
        } catch (Exception exception) {
            throw new EntityNotFoundException();
        }
    }

    public Hint updateHint(Hint hint) {
        return hintRepository.save(hint);
    }

    public List<Hint> getAllHints() {
        return hintRepository.findAll();
    }

    public List<Hint> createHints(List<Hint> hints) {
        return hintRepository.saveAll(hints);
    }
}