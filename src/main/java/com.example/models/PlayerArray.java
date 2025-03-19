package com.example.models;

import java.util.ArrayList;
import java.util.List;

public class PlayerArray {
    private String playerName;
    private List<List<Integer>> frames;
    private List<Integer> scores;
    private List<Integer> totalScoreFrame;
    private int totalScore;

    public PlayerArray(String playerName) {
        this.playerName = playerName;
        this.frames = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.totalScoreFrame = new ArrayList<>();
        this.totalScore = 0;

        for (int i = 0; i < 5; i++) {
            frames.add(new ArrayList<>());
            scores.add(0);
            totalScoreFrame.add(0);
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<List<Integer>> getFrames() {
        return frames;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public List<Integer> getTotalScoreFrame() {
        return totalScoreFrame;
    }

    public void setTotalScoreFrame(int index, int score) {
        totalScoreFrame.set(index, score);
    }
}
