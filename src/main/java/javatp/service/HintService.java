package javatp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javatp.domain.Hint;
import javatp.exception.IncompleteObjectException;
import javatp.repository.HintRepository;

@Service
public class HintService {
    @Autowired
    HintRepository hintRepository;

    public Hint getHint(Long id) {
        return hintRepository.getOne(id);
    }

    public boolean hintExistsByID(Long id){
        return hintRepository.existsById(id);
    }

    public Hint createHint(Hint hint) {
        if(hint.getDescription()!="" && hint.getId()!=0 && hint.getPoi() != null){
            return hintRepository.save(hint);
        } else
        throw new IncompleteObjectException("All properties are required");
    }

    public Boolean deleteHint(Hint hint) {
        try {
            hintRepository.delete(hint);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    public Hint updateHint(Hint hint){
        return hintRepository.save(hint);
    }

    public List<Hint> getAllHints(){
        return hintRepository.findAll();
    }

    public List<Hint> createHints(List<Hint> hints){
        return hintRepository.saveAll(hints);
    }
}