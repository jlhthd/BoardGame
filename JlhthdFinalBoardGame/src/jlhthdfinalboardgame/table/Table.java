/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.table;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author jlhth_000
 * @param <E>
 */
public class Table<E extends TableGeneric> implements Serializable{
    private final ArrayList<ArrayList<E>> table;
    private final int numRows;
    private final int numCols;
    
    public Table(int numRows, int numCols, Class<E> typeParameter) {
        this.numRows = numRows;
        this.numCols = numCols;
        table = new ArrayList<>();
        
        for (int r = 0; r < numRows; r++) {
            table.add(new ArrayList<>());
            for (int c = 0; c < numCols; c++) {
                E a = (E) GenericFactory.createElement(typeParameter);
                table.get(r).add(a);
            }
        }
    }
    
    public E get(int row, int col) {
        try {
            if(numRows >= row && numCols >= col) {
                return this.table.get(row).get(col);
            } else {
                throw new Exception("No cell exists");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return this.table.get(row).get(col);
        }
    }
    
    public int getNumRows() {
        return this.numRows;
    }
    
    public int getNumCols() {
        return this.numCols;
    }
    
    @Override
    public String toString() {
        String string = "";
        
        for(int r = 0; r < numRows; r++) {
            for(int c = 0; c < numCols; c++) {
                string = string + " "+ this.get(r, c).toString();
            }
            string = string + "\n";
        }
        
        return string;
    }
}
