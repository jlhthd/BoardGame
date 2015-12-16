/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.boardmanagement;

import java.io.Serializable;
import jlhthdfinalboardgame.table.BeadColors;
import jlhthdfinalboardgame.table.PlayerColors;

/**
 *
 * @author jlhth_000
 */
public class Player implements Serializable{
    private final PlayerColors color;
    private final Integer startingBeads;
    private Integer remainingBeads;
    private BeadColors selectedColor;
    private int score;
    //should eventually be changable for asymetrical scoring
    private BeadColors[] scoreArray = {BeadColors.BLUE, BeadColors.GREEN, BeadColors.RED, BeadColors.YELLOW};
    
    public Player(PlayerColors color, Integer startingBeads) {
        this.startingBeads = startingBeads;
        this.remainingBeads = startingBeads;
        this.color = color;
        this.selectedColor = BeadColors.NONE;
    }
    
    public void resetBeads() {
        remainingBeads = startingBeads;
        score = 0;
    }
    
    public PlayerColors getPlayerColor() {
        return color;
    }
    
    public Integer getRemainingBeads() {
        return remainingBeads;
    }
    
    public void playBead() {
        remainingBeads--;
    }
    
    public void setSelectedColor(BeadColors color) {
        this.selectedColor = color;
    }
    
    public BeadColors getSelectedColor() {
        return selectedColor;
    }
    
    public int getScore() {
        return score;
    }
    
    public void addToScore(int points) {
        score += points;
    }
    
    public BeadColors[] getScoreArray() {
        return scoreArray;
    }
}
