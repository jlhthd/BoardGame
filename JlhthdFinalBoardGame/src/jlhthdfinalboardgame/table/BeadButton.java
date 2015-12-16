/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.table;

import javafx.scene.control.Button;

/**
 *
 * @author jlhth_000
 */

//needed for table polymorphism
public class BeadButton extends Button implements TableGeneric{
    private String color;
    private String border;
    private final String defaults;
    
    public BeadButton() {
        defaults = "-fx-focus-color: transparent; -fx-border-width: 3;";
        color = "-fx-body-color: grey";
        border = "-fx-border-color: black";
    }
    
    private void generateStyle() {
        String style = defaults + " " + color + " " + border;
        this.setStyle(style);
    }
    
    public void setColor(String color) {
        this.color = "-fx-body-color: " + color + ";";
        this.generateStyle();
    }
    
    public void setBorder(String border) {
        this.border = "-fx-border-color: " + border + ";";
        this.generateStyle();
    }
}
