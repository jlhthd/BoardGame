/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.table;

/**
 *
 * @author jlhth_000
 */
public class GenericFactory {
    
    public static TableGeneric createElement(Class type) {
        try {
            if(type.equals(PlayerColorObject.class)) {
                return new PlayerColorObject();
            } else if(type.equals(BeadColorObject.class)) {
                return new BeadColorObject();
            } else if(type.equals(BeadButton.class)) {
                return new BeadButton();
            } else {
                throw new Exception("Invalid type");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
