package com.example.services;

import com.example.models.PlayerArray;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {
    private final List<PlayerArray> playerArrays = new ArrayList<>();
    private int currentPlayer = 0;
    private int currentFrame = 0;

    public void addPlayer(String name) {
        playerArrays.add(new PlayerArray(name));
    }

    public List<PlayerArray> getPlayers() {
        return playerArrays;
    }

    public void lancer(int quilles) {
        if (playerArrays.isEmpty()) return;

        PlayerArray player = playerArrays.get(currentPlayer);

        if (hasFinished(player)) {
            System.out.println("Le player " + player.getPlayerName() + " a déjà terminé.");
            currentPlayer = (currentPlayer + 1) % playerArrays.size();
            return;
        }

        List<Integer> frame = player.getFrames().get(currentFrame);

        validateLancer(quilles, frame, currentFrame);

        frame.add(quilles);

        if (isFrameComplete(frame, currentFrame, quilles)) {
            calculateScore(player);
            currentPlayer = (currentPlayer + 1) % playerArrays.size();
            if (currentPlayer == 0 && currentFrame < 4) {
                currentFrame++;
            }
        }
    }

    private boolean hasFinished(PlayerArray player) {
        return player.getFrames().size() > 4 && player.getFrames().get(4).size() == 4;
    }


    private void validateLancer(int quilles, List<Integer> frame, int frameIndex) {
        if (quilles < 0 || quilles > 15) {
            throw new IllegalArgumentException("Le nombre de quilles doit être compris entre 0 et 15.");
        }

        int sumFrame = frame.stream().mapToInt(Integer::intValue).sum();

        if (frameIndex < 4) {
            if (sumFrame + quilles > 15) {
                throw new IllegalArgumentException("Le total des quilles dans une frame ne peut pas dépasser 15.");
            }
        } else if (frameIndex == 4) {
            boolean chainBroken = isChainBroken(frame);
            int sumAfterBreak = sumAfterBreak(frame);
            if (chainBroken && quilles == 15) {
                throw new IllegalArgumentException("Impossible de faire un strike après avoir cassé la chaîne en frame 5.");
            }
            if (chainBroken && sumAfterBreak + quilles > 15) {
                throw new IllegalArgumentException("Le total des lancers après cassure en frame 5 ne peut pas dépasser 15.");
            }
        }
    }

    private boolean isChainBroken(List<Integer> frame) {
        for (Integer score : frame) {
            if (score != 15) {
                return true;
            }
        }
        return false;
    }

    private int sumAfterBreak(List<Integer> frame) {
        boolean broken = false;
        int sum = 0;
        for (Integer score : frame) {
            if (!broken && score != 15) {
                broken = true;
                sum += score;
            } else if (broken) {
                sum += score;
            }
        }
        return sum;
    }

    private boolean isFrameComplete(List<Integer> frame, int frameIndex, int lastThrow) {
        if (frameIndex == 4) {
            if (frame.size() == 4) {
                return true;
            }
            if (isChainBroken(frame)) {
                return sumAfterBreak(frame) == 15;
            }
            return false;
        } else {
            int sumFrame = frame.stream().mapToInt(Integer::intValue).sum();
            if (frame.size() == 1 && lastThrow == 15) {
                return true;
            } else if (sumFrame == 15) {
                return true;
            } else if (frame.size() == 3) {
                return true;
            }
            return false;
        }
    }

    private void calculateScore(PlayerArray player) {
        int total = 0;
        for (int i = 0; i <= currentFrame; i++) {
            List<Integer> frame = player.getFrames().get(i);
            int frameScore = frame.stream().mapToInt(Integer::intValue).sum();

            if (i < 4) {
                if (frame.size() == 1 && frame.get(0) == 15) {
                    frameScore += bonusStrike(player, i);
                } else if (frameScore == 15) {
                    frameScore += bonusSpare(player, i);
                }
            }
            player.getScores().set(i, frameScore);
            total += frameScore;
            player.setTotalScoreFrame(i, total);
        }
        player.setTotalScore(total);
    }

    private int bonusStrike(PlayerArray player, int index) {
        int bonus = 0;
        int lancersPris = 0;
        for (int i = index + 1; i < 5 && lancersPris < 3; i++) {
            List<Integer> nextFrame = player.getFrames().get(i);
            for (int nbQuilles : nextFrame) {
                bonus += nbQuilles;
                lancersPris++;
                if (lancersPris == 3) break;
            }
        }
        return bonus;
    }

    private int bonusSpare(PlayerArray player, int index) {
        int bonus = 0;
        int lancersPris = 0;
        for (int i = index + 1; i < 5 && lancersPris < 2; i++) {
            List<Integer> nextFrame = player.getFrames().get(i);
            for (int nbQuilles : nextFrame) {
                bonus += nbQuilles;
                lancersPris++;
                if (lancersPris == 2) break;
            }
        }
        return bonus;
    }
}
