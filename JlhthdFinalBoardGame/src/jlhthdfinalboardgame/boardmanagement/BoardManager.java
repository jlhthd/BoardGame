/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.boardmanagement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import jlhthdfinalboardgame.table.BeadColorObject;
import jlhthdfinalboardgame.table.BeadColors;
import jlhthdfinalboardgame.table.PlayerColorObject;
import jlhthdfinalboardgame.table.PlayerColors;
import jlhthdfinalboardgame.table.Table;
import jlhthdfinalboardgame.table.TableGeneric;

/**
 *
 * @author jlhth_000
 */
public final class BoardManager {
    private static ArrayList<Player> players;
    private static Player currentPlayer;
    private static Integer currentTurn;
    private static Phase currentPhase;
    private static int beadsPlayed;
    private static Table<PlayerColorObject> playerTable;
    private static Table<BeadColorObject> beadTable;
    
    public static final int COLUMNS = 5;
    public static final int ROWS = 8;
    private static final String SAVEPATH = "save.sv";
    
    public enum Phase {
        PLAYER,
        COLOR,
        FINAL
    }
    
    private static class BoardManagerHolder {
        private static final BoardManager INSTANCE = new BoardManager();
    }
    
    private BoardManager() {
        //get the number of beads each player can play
        Integer numBeads = this.getBoardSize()/2 - 1;
        
        //create players
        players = new ArrayList<>();
        players.add(new Player(PlayerColors.WHITE, numBeads));
        players.add(new Player(PlayerColors.BLACK, numBeads));
        
        //set starting player to white
        currentPlayer = players.get(0);
        
        //set starting phase
        currentPhase = Phase.PLAYER;
        
        //create bead tables
        playerTable = new Table<>(ROWS, COLUMNS, PlayerColorObject.class);
        beadTable = new Table<>(ROWS + 1, COLUMNS + 1, BeadColorObject.class);
        
        //set the number of color beads played to 0
        beadsPlayed = 0;
    }
    
    public static BoardManager getInstance() {
        return BoardManagerHolder.INSTANCE;
    }
    
    public void changeTurns() {
        if(currentPlayer.equals(players.get(0))) currentPlayer = players.get(1);
        else currentPlayer = players.get(0);
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public Integer getCurrentTurn() {
        return currentTurn;
    }
    
    public boolean checkIfPlayerBeadActive(int row, int col) {
        //check to make sure bead hasn't been claimed
        if(getPlayerAt(row, col) != PlayerColors.NONE) return false;
        
        //check each corner and return true as soon as a claimed bead is found
        if(row > 0 && col > 0) {
            if(getPlayerAt(row - 1, col - 1) != PlayerColors.NONE) return true;
        }
        if(row > 0 && col < COLUMNS - 1) {
            if(getPlayerAt(row - 1, col + 1) != PlayerColors.NONE) return true;
        }
        if(row < ROWS - 1 && col > 0) {
            if(getPlayerAt(row + 1, col - 1) != PlayerColors.NONE) return true;
        }
        if(row < ROWS - 1 && col < COLUMNS - 1) {
            if(getPlayerAt(row + 1, col + 1) != PlayerColors.NONE) return true;
        }
        
        //check each side and return true as soon as a claimed bead is found
        if(row > 0) {
            if(getPlayerAt(row - 1, col) != PlayerColors.NONE) return true;
        }
        if(row < ROWS - 1) {
            if(getPlayerAt(row + 1, col) != PlayerColors.NONE) return true;
        }
        if(col > 0) {
            if(getPlayerAt(row, col - 1) != PlayerColors.NONE) return true;
        }
        if(col < COLUMNS - 1) {
            if(getPlayerAt(row, col + 1) != PlayerColors.NONE) return true;
        }
        
        //if no claimed bead is found return false
        return false;
    }
    
    public boolean checkIfColorBeadActive(int targetRow, int targetCol, int row, int col) {
        //check to make sure bead hasn't been claimed
        if(getBeadAt(row, col) != BeadColors.NONE) return false;
        
        //check each corner and return true as soon as a claimed bead is found
        if(row == targetRow && col == targetCol) return true;
        if(row == targetRow && col == targetCol + 1) return true;
        if(row == targetRow + 1 && col == targetCol) return true;
        if(row == targetRow + 1 && col == targetCol + 1) return true;
        
        //if no claimed bead is found return false
        return false;
    }
    
    public boolean checkIfColorBeadActive(int row, int col) {
        if(getBeadAt(row, col) != BeadColors.NONE) return false;
        return true;
    }
    
    public void clear() {
        //clear player beads
        for(int r = 0; r < ROWS; r++) {
            for(int c = 0; c < COLUMNS; c++) {
                playerTable.get(r, c).setColor(PlayerColors.NONE);
            }
        }
        
        //clear color beads
        for(int r = 0; r <= ROWS; r++) {
            for(int c = 0; c <= COLUMNS; c++) {
                beadTable.get(r, c).setColor(BeadColors.NONE);
            }
        }
        
        //reset game positions
        resetPlayers();
        resetPhase();
    }
    
    public void resetPlayers() {
        //reset starting player and board poisition
        currentPlayer = players.get(0);
        players.get(0).resetBeads();
        players.get(1).resetBeads();
    }
    
    public void resetPhase() {
        //reset starting phase
        currentPhase = Phase.PLAYER;
    }
    
    public void changePhase() {
        //switch between first two phases
        if(currentPhase == Phase.PLAYER) {
            currentPhase = Phase.COLOR;
        } else if(currentPhase == Phase.COLOR) {
            currentPhase = Phase.PLAYER;
        }
    }
    
    public void changeFinalPhase() {
        currentPhase = Phase.FINAL;
    }
    
    public Phase getCurrentPhase() {
        return currentPhase;
    }
    
    public void save() throws IOException {
        //serialize the board
        try (FileOutputStream fileOut = new FileOutputStream(SAVEPATH); ObjectOutputStream obOut = new ObjectOutputStream(fileOut)) {
            obOut.writeObject(players);
            obOut.writeObject(currentPlayer);
            obOut.writeObject(currentPhase);
            obOut.writeInt(beadsPlayed);
            obOut.writeObject(playerTable);
            obOut.writeObject(beadTable);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void clearSave() throws IOException {
        //clear all objects in save file
        try (FileOutputStream fileOut = new FileOutputStream(SAVEPATH); ObjectOutputStream obOut = new ObjectOutputStream(fileOut)) {
            obOut.writeObject(null);
            obOut.writeObject(null);
            obOut.writeObject(null);
            obOut.writeInt(0);
            obOut.writeObject(null);
            obOut.writeObject(null);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public boolean checkForSave() throws ClassNotFoundException {
        //check if the first object is null
        try (FileInputStream fileIn = new FileInputStream(SAVEPATH); ObjectInputStream obIn = new ObjectInputStream(fileIn)) {
            if(obIn.readObject() == null) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
    
    public void load() throws IOException, ClassNotFoundException {
        //deserialize the board
        try (FileInputStream fileIn = new FileInputStream(SAVEPATH); ObjectInputStream obIn = new ObjectInputStream(fileIn)) {
            players = (ArrayList<Player>)obIn.readObject();
            currentPlayer = (Player)obIn.readObject();
            currentPhase = (Phase)obIn.readObject();
            beadsPlayed = obIn.readInt();
            playerTable = (Table<PlayerColorObject>)obIn.readObject();
            beadTable = (Table<BeadColorObject>)obIn.readObject();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void scoreGame() {
        //score every square
        for(int r = 0; r < ROWS; r++) {
            for(int c = 0; c < COLUMNS; c++) {
                //get square at coordinates
                ArrayList<TableGeneric> array = getSquareScoring(r, c);
                PlayerColorObject playerColor = (PlayerColorObject)array.get(0);
                
                //get player controlling that square
                Player player;
                switch(playerColor.getColor()) {
                    case WHITE:
                        player = players.get(0);
                        break;
                    case BLACK:
                        player = players.get(1);
                        break;
                    default:
                        player = null;
                }
                
                //score the square
                if(player != null) {
                    BeadColors[] scoreArray = player.getScoreArray().clone();

                    for(int i = 1; i < array.size(); i++) {
                        BeadColorObject compareColor = (BeadColorObject)array.get(i);
                        int count = 0;

                        //cycle through scoreArray to check matches with score list
                        for(int j = 0; j < 4; j++) {
                            if(scoreArray[j] == compareColor.getColor()) {
                                count++;
                                scoreArray[j] = BeadColors.NONE;
                                break;
                            }
                        }

                        //add count to player's score
                        player.addToScore(count);
                    }
                }
            }
        }
    }
    
    public int getWhiteScore() {
        return players.get(0).getScore();
    }
    
    public int getBlackScore() {
        return players.get(1).getScore();
    }
    
    public void setPlayerAt(int row, int col, PlayerColors color) {
        try{
            PlayerColorObject square = playerTable.get(row, col);
            if(square == null) throw new Exception("Invalid player square");
            
            square.setColor(color);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }        
    }
    
    public PlayerColors getPlayerAt(int row, int col) {
        try{
            PlayerColorObject square = playerTable.get(row, col);
            if(square == null) throw new Exception("Invalid player square");
            
            return square.getColor();
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        } 
    }
    
    public void setBeadAt(int row, int col, BeadColors color) {
        try{
            BeadColorObject corner = beadTable.get(row, col);
            if(corner == null) throw new Exception("Invalid corner");
            
            corner.setColor(color);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }     
    }
    
    public BeadColors getBeadAt(int row, int col) {
        try{
            BeadColorObject corner = beadTable.get(row, col);
            if(corner == null) throw new Exception("Invalid corner");
            
            return corner.getColor();
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        } 
    }
    
    public ArrayList<TableGeneric> getSquareScoring(int row, int col) {
        try{
            PlayerColorObject player = playerTable.get(row, col);
            if(player == null) throw new Exception("Invalid square");
            
            //get the controlling player and each adjacent color bead
            ArrayList<TableGeneric> scoreList = new ArrayList<>();
            scoreList.add(player);
            scoreList.add(beadTable.get(row, col));
            scoreList.add(beadTable.get(row + 1, col));
            scoreList.add(beadTable.get(row, col + 1));
            scoreList.add(beadTable.get(row + 1, col + 1));
            
            return scoreList;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
    
    public Integer getBoardSize() {
        return COLUMNS * ROWS;
    }
    
    public String playerToString() {
        return playerTable.toString();
    }
    
    public String colorToString() {
        return beadTable.toString();
    }
    
    public void setBeadsPlayed(int beads) {
        beadsPlayed = beads;
    }
    
    public int getBeadsPlayed() {
        return beadsPlayed;
    }
    
    public void playBead() {
        beadsPlayed++;
    }
    
    public PlayerColors managePlayerBeadPlayed() {
        currentPlayer.playBead();
        return currentPlayer.getPlayerColor();
    }
}
