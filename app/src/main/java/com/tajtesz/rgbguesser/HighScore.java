package com.tajtesz.rgbguesser;

/**
 * Created by PC on 2017.10.13..
 */

public class HighScore {

    private String name;
    private int score;

    public HighScore(String username, int score) {
        this.name = username;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

}
