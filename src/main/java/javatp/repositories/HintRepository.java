package javatp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javatp.entities.Hint;

@Repository
public interface HintRepository extends JpaRepository<Hint, Long> {

}