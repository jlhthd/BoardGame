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
public class BeadColorObject implements TableGeneric, Serializable {
    private BeadColors color;
    
    public BeadColorObject() {
        color = BeadColors.NONE;
    }
    
    public void setColor(BeadColors color) {
        this.color = color;
    }
    
    public BeadColors getColor() {
        return color;
    }
    
    @Override
    public String toString() {
        return color.toString();
    }
}
