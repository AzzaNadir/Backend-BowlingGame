package com.example.ServicesTest;

import com.example.models.PlayerArray;
import com.example.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
        gameService.addPlayer("Joueur1");
    }

    @Test
    void testPartieSansStrikeNiSpare() {
        gameService.lancer(5);
        gameService.lancer(4);
        gameService.lancer(3);

        gameService.lancer(8);
        gameService.lancer(2);
        gameService.lancer(1);

        gameService.lancer(1);
        gameService.lancer(4);
        gameService.lancer(5);

        gameService.lancer(4);
        gameService.lancer(2);
        gameService.lancer(2);

        gameService.lancer(2);
        gameService.lancer(2);
        gameService.lancer(2);
        gameService.lancer(2);

        PlayerArray player = gameService.getPlayers().get(0);
        assertEquals(49, player.getTotalScore());
    }

    @Test
    void testStrike() {
        gameService.lancer(15);

        gameService.lancer(3);
        gameService.lancer(5);
        gameService.lancer(2);

        PlayerArray player = gameService.getPlayers().get(0);
        assertEquals(35, player.getTotalScore());
    }

    @Test
    void testSpare() {
        gameService.lancer(1);
        gameService.lancer(2);
        gameService.lancer(12);

        gameService.lancer(8);
        gameService.lancer(7);

        gameService.lancer(1);
        gameService.lancer(2);
        gameService.lancer(1);

        PlayerArray player = gameService.getPlayers().get(0);
        assertEquals(52, player.getTotalScore());
    }


    @Test
    void testPartieCompleteAvecStrikeEtSpare() {
        gameService.lancer(15);

        gameService.lancer(8);
        gameService.lancer(1);
        gameService.lancer(2);

        gameService.lancer(1);
        gameService.lancer(2);
        gameService.lancer(12);

        gameService.lancer(6);
        gameService.lancer(4);
        gameService.lancer(1);

        gameService.lancer(15);
        gameService.lancer(8);
        gameService.lancer(2);
        gameService.lancer(3);

        PlayerArray player = gameService.getPlayers().get(0);
        assertEquals(101, player.getTotalScore());
    }

    @Test
    void testZeroScoreGame() {
        for (int i = 0; i < 8; i++) {
            gameService.lancer(0);
        }
        PlayerArray player = gameService.getPlayers().get(0);
        assertEquals(0, player.getTotalScore());
    }

    @Test
    void testPerfectGame() {
        for (int i = 0; i < 8; i++) {
            gameService.lancer(15);
        }
        PlayerArray player = gameService.getPlayers().get(0);
        assertEquals(300, player.getTotalScore());
    }

    @Test
    void testMultiplePlayers() {
        gameService.addPlayer("Joueur2");

        // Le joueur 1
        gameService.lancer(1);
        gameService.lancer(2);
        gameService.lancer(12);
        // Le joueur 2
        gameService.lancer(3);
        gameService.lancer(4);
        gameService.lancer(8);

        PlayerArray player1 = gameService.getPlayers().get(0);
        PlayerArray player2 = gameService.getPlayers().get(1);

        assertEquals(15, player1.getScores().get(0));
        assertEquals(15, player2.getScores().get(0));
    }
}
