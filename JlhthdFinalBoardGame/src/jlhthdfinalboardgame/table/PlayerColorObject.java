/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.table;

import java.io.Serializable;

/**
 *
 * @author jlhth_000
 */
public class PlayerColorObject implements TableGeneric, Serializable{
    private PlayerColors color;
    
    public PlayerColorObject() {
        color = PlayerColors.NONE;
    }
    
    public void setColor(PlayerColors color) {
        this.color = color;
    }
    
    public PlayerColors getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return color.toString();
    }
}
