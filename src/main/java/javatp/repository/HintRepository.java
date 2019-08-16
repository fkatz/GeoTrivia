package javatp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javatp.domain.Hint;

@Repository
public interface HintRepository extends JpaRepository<Hint, Long> {
}