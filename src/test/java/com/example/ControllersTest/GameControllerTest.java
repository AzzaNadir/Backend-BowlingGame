package com.example.ControllersTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String playerTest = "TestJoueur";

    @BeforeAll
    void setup() {
        String url = "http://localhost:" + port + "/game/addPlayer/" + playerTest;
        ResponseEntity<Void> response = restTemplate.postForEntity(url, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAjouterJoueur() {
        String url = "http://localhost:" + port + "/game/addPlayer/NewPlayer";
        ResponseEntity<Void> response = restTemplate.postForEntity(url, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetJoueurs() {
        String url = "http://localhost:" + port + "/game/players";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testEffectuerLancer() {
        String url = "http://localhost:" + port + "/game/lancer/8";
        ResponseEntity<Void> response = restTemplate.postForEntity(url, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEffectuerLancerAvecValeurInvalide() {
        String url = "http://localhost:" + port + "/game/lancer/20";
        ResponseEntity<Void> response = restTemplate.postForEntity(url, null, Void.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
