package javatp.application;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javatp.entities.User;
import javatp.repositories.POIRepository;
import javatp.entities.POI;

@SpringBootApplication
@RestController
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private AuthenticationManager auth;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostMapping(value = "/poi")
	public ResponseEntity<Object> createPOI(@RequestBody POI poi,
			@RequestHeader(value = "Authorization") String token) {
		try {
			auth.authenticateToken(token);
			POIRepository.get().save(poi);
			return ResponseEntity.ok(poi);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage(), null));
		}
	}

	@GetMapping(value = "/pois")
	public ResponseEntity<Object> getPOIs(@RequestHeader(value = "Authorization") String token) {
		try {
			auth.authenticateToken(token);
			return ResponseEntity.ok(POIRepository.get().getAll());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage(), null));
		}
	}

	@PostMapping(value = "/auth")
	public ResponseEntity<Object> auth(@RequestBody User user) {
		try {
			String token = auth.generateToken(user);
			logger.info(token);
			return new ResponseEntity<>(token, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new Message(e.getMessage(), null));
		}
	}

}
