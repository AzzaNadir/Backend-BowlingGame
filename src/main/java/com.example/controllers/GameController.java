package com.example.controllers;

import com.example.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {

    @Autowired
    private GameService gameService;
    @GetMapping("/players")
    public ResponseEntity<?> getPlayers() {
        return ResponseEntity.ok(gameService.getPlayers());
    }

    @PostMapping("/addPlayer/{nom}")
    public ResponseEntity<Void> addPlayer(@PathVariable String nom) {
        gameService.addPlayer(nom);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/lancer/{quilles}")
    public ResponseEntity<?> effectuerLancer(@PathVariable int quilles) {
        try {
            gameService.lancer(quilles);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
