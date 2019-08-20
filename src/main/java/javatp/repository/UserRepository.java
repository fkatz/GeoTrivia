package javatp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javatp.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(@Param("username") String username);

    @Query("SELECT COUNT(user)>0 FROM User user WHERE user.username=?1")
    boolean existsByUsername(String username);
}