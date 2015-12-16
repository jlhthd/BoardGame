/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.scenemanagement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;
import jlhthdfinalboardgame.boardmanagement.BoardManager;
import jlhthdfinalboardgame.boardmanagement.Player;
import jlhthdfinalboardgame.table.BeadButton;
import jlhthdfinalboardgame.table.BeadColors;
import jlhthdfinalboardgame.table.PlayerColors;
import jlhthdfinalboardgame.table.Table;

/**
 * FXML Controller class
 *
 * @author jlhth_000
 */
public class BoardController extends LoadableScene implements Initializable {
    
    private BoardManager boardManager;
    
    @FXML
    private AnchorPane centerPane;
    
    @FXML
    private HBox blueHBox;
    
    @FXML
    private HBox redHBox;
    
    @FXML
    private HBox greenHBox;
    
    @FXML
    private HBox yellowHBox;
    
    @FXML
    private Label whiteCurrent;
    
    @FXML
    private Label blackCurrent;
    
    private BeadButton blueButton;
    private BeadButton redButton;
    private BeadButton greenButton;
    private BeadButton yellowButton;
    
    private Table<BeadButton> playerBeads;
    private Table<BeadButton> colorBeads;
    
    //color constants
    private static final String BLUE = "blue";
    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String YELLOW = "yellow";
    private static final String UNSELECTEDPLAYER = "grey";
    private static final String UNSELECTEDCOLOR = "lightgrey";
    private static final String BORDERBLUE = "lightblue";
    private static final String BORDERBLACK = "black";
    private static final String PLAYERWHITE = "white";
    private static final String PLAYERBLACK = "black";
    private static final String COLORSELECTBLUE = "blue";
    private static final String COLORSELECTRED = "red";
    private static final String COLORSELECTGREEN = "green";
    private static final String COLORSELECTYELLOW = "yellow";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boardManager = BoardManager.getInstance();
        
        //create grid of buttons
        GridPane boardGrid = new GridPane();
        playerBeads = new Table<>(BoardManager.ROWS, BoardManager.COLUMNS, BeadButton.class);
        colorBeads = new Table<>(BoardManager.ROWS + 1, BoardManager.COLUMNS + 1, BeadButton.class);
        
        boardGrid.setAlignment(Pos.CENTER);
        
        //set grid anchors
        AnchorPane.setTopAnchor(boardGrid, 0d);
        AnchorPane.setBottomAnchor(boardGrid, 0d);
        AnchorPane.setLeftAnchor(boardGrid, 0d);
        AnchorPane.setRightAnchor(boardGrid, 0d);
        
        //set grid spacing
        for(int r = 0; r <= (BoardManager.ROWS) * 2; r++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100 * 1/((BoardManager.ROWS + 1) * 2));
            boardGrid.getRowConstraints().add(row);
        }
        for(int c = 0; c <= (BoardManager.COLUMNS) * 2; c++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100 * 1/((BoardManager.COLUMNS + 1) * 2));
            boardGrid.getColumnConstraints().add(col);
        }
        
        //add player bead buttons
        for(int r = 0; r < BoardManager.ROWS; r++) {
            for(int c = 0; c < BoardManager.COLUMNS; c++){
                //get button
                BeadButton temp = playerBeads.get(r, c);
                
                //set button properties
                double radius = 15d;
                temp.setId(r + "," + c);
                temp.setShape(new Circle(radius));
                temp.setMaxSize(radius * 2, radius * 2);
                temp.setMinSize(radius * 2, radius * 2);
                temp.setColor(UNSELECTEDPLAYER);
                
                //set button action
                temp.setOnAction((event) -> {
                    try {
                        this.handlePlayerButton(event);
                    } catch (IOException ex) {
                        Logger.getLogger(BoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                //add button to grid
                boardGrid.add(temp, c * 2 + 1, r * 2 + 1);
            }
        }
        
        //add color bead buttons
        for(int r = 0; r <= BoardManager.ROWS; r++) {
            for(int c = 0; c <= BoardManager.COLUMNS; c++){
                //get button
                BeadButton temp = colorBeads.get(r, c);
                
                //set button properties
                double radius = 10d;
                temp.setId(r + "," + c);
                temp.setShape(new Circle(radius));
                temp.setMaxSize(radius * 2, radius * 2);
                temp.setMinSize(radius * 2, radius * 2);
                temp.setColor(UNSELECTEDCOLOR);
                
                //set button action
                temp.setOnAction((event) -> {
                    try {
                        this.handleColorButton(event);
                    } catch (IOException ex) {
                        Logger.getLogger(BoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
                //add button to grid
                boardGrid.add(temp, c*2, r*2);
            }
        }
        
        centerPane.getChildren().add(boardGrid);
        
        //add color select buttons to their hboxes
        blueButton = new BeadButton();
        blueHBox.getChildren().add(blueButton);
        redButton = new BeadButton();
        redHBox.getChildren().add(redButton);
        greenButton = new BeadButton();
        greenHBox.getChildren().add(greenButton);
        yellowButton = new BeadButton();
        yellowHBox.getChildren().add(yellowButton);
        
        //set color select button radius
        double radius = 20d;
        
        //set color select button properties
        colorSelectButtonGenerate(radius, blueButton, COLORSELECTBLUE);
        colorSelectButtonGenerate(radius, redButton, COLORSELECTRED);
        colorSelectButtonGenerate(radius, greenButton, COLORSELECTGREEN);
        colorSelectButtonGenerate(radius, yellowButton, COLORSELECTYELLOW);
        
        //set color select button ids
        blueButton.setId("blue");
        redButton.setId("red");
        greenButton.setId("green");
        yellowButton.setId("yellow");
        
        //set color select button actions
        blueButton.setOnAction(this::handleColorSelectButton);
        redButton.setOnAction(this::handleColorSelectButton);
        greenButton.setOnAction(this::handleColorSelectButton);
        yellowButton.setOnAction(this::handleColorSelectButton);
    }
    
    private void reloadVisuals() {
        //add player bead buttons
        for(int r = 0; r < BoardManager.ROWS; r++) {
            for(int c = 0; c < BoardManager.COLUMNS; c++){
                PlayerColors color = boardManager.getPlayerAt(r, c);
                setPlayerBead(r, c, color);
            }
        }
        
        //add color bead buttons
        for(int r = 0; r <= BoardManager.ROWS; r++) {
            for(int c = 0; c <= BoardManager.COLUMNS; c++){
                BeadColors color = boardManager.getBeadAt(r, c);
                setColorBead(r, c, color);
            }
        }
    }
    
    private void colorSelectButtonGenerate(double radius, BeadButton button, String color) {
        //set color select button properties
        button.setShape(new Circle(radius));
        button.setMaxSize(radius * 2, radius * 2);
        button.setMinSize(radius * 2, radius * 2);
        button.setColor(color);
        button.setBorder(BORDERBLACK);
    }
    
    private void setPlayerBead(int row, int col, PlayerColors color) {
        //set bead color in manager
        boardManager.setPlayerAt(row, col, color);
        
        //set button color
        String colorString;
        switch(color) {
            case WHITE:
                colorString = PLAYERWHITE;
                break;
            case BLACK:
                colorString = PLAYERBLACK;
                break;
            default:
                colorString = UNSELECTEDPLAYER;
                break;
        }
        playerBeads.get(row, col).setColor(colorString);
    }
    
    private void setColorBead(int row, int col, BeadColors color) {
        //set bead color in manager
        boardManager.setBeadAt(row, col, color);
        
        //set button color
        String colorString;
        switch(color) {
            case BLUE:
                colorString = BLUE;
                break;
            case YELLOW:
                colorString = YELLOW;
                break;
            case RED:
                colorString = RED;
                break;
            case GREEN:
                colorString = GREEN;
                break;
            default:
                colorString = UNSELECTEDCOLOR;
                break;
        }
        colorBeads.get(row, col).setColor(colorString);
    }
    
    private void handlePlayerButton(ActionEvent event) throws IOException {
        //get the source button coordinates
        BeadButton button = (BeadButton)event.getSource();
        String[] coords = button.getId().split(",");
        int row = Integer.parseInt(coords[0]);
        int col = Integer.parseInt(coords[1]);
        
        //play bead and get player color
        PlayerColors color = boardManager.managePlayerBeadPlayed();
        
        //set the bead color in manager and set button color
        setPlayerBead(row, col, color);
        
        //switch phases
        setColorBeadsActive(row, col);
        boardManager.changePhase();
    }
    
    private void handleColorButton(ActionEvent event) throws IOException {
        //get the source button coordinates
        BeadButton button = (BeadButton)event.getSource();
        String[] coords = button.getId().split(",");
        int row = Integer.parseInt(coords[0]);
        int col = Integer.parseInt(coords[1]);
        
        //get currently selected color
        BeadColors selectedColor = boardManager.getCurrentPlayer().getSelectedColor();
        if(selectedColor == BeadColors.NONE) return;
        
        //set button to that color
        setColorBead(row, col, selectedColor);
        
        //play bead and change phases
        boardManager.playBead();
        boardManager.changeTurns();
        setPlayerBeadsActive();
        boardManager.changePhase();
    }
    
    private void handleColorSelectButton(ActionEvent event) {
        //get selected color
        BeadButton button = (BeadButton)event.getSource();
        String name = button.getId();
        
        //set selected color
        switch(name) {
            case "blue":
                boardManager.getCurrentPlayer().setSelectedColor(BeadColors.BLUE);
                setSelectedColorSelectButton(BeadColors.BLUE);
                break;
            case "red":
                boardManager.getCurrentPlayer().setSelectedColor(BeadColors.RED);
                setSelectedColorSelectButton(BeadColors.RED);
                break;
            case "yellow":
                boardManager.getCurrentPlayer().setSelectedColor(BeadColors.YELLOW);
                setSelectedColorSelectButton(BeadColors.YELLOW);
                break;
            case "green":
                boardManager.getCurrentPlayer().setSelectedColor(BeadColors.GREEN);
                setSelectedColorSelectButton(BeadColors.GREEN);
                break;
            default:
                boardManager.getCurrentPlayer().setSelectedColor(BeadColors.NONE);
                setSelectedColorSelectButton(BeadColors.NONE);
                break;
        }
    }
    
    private void setSelectedColorSelectButton(BeadColors color) {
        //reset selection border
        blueButton.setBorder(BORDERBLACK);
        redButton.setBorder(BORDERBLACK);
        greenButton.setBorder(BORDERBLACK);
        yellowButton.setBorder(BORDERBLACK);
        
        //set selected button to button
        BeadButton button;
        switch(color) {
            case BLUE:
                button = blueButton;
                break;
            case RED:
                button = redButton;
                break;
            case GREEN:
                button = greenButton;
                break;
            case YELLOW:
                button = yellowButton;
                break;
            default:
                button = null;
                break;
        }
        
        //if a button is selected, set border to selected border
        if(button != null) {
            button.setBorder(BORDERBLUE);
        }
    }
    
    public void clear() throws IOException {
        boardManager.clear();
        
        //clear player bead buttons
        for(int r = 0; r < BoardManager.ROWS; r++) {
            for(int c = 0; c < BoardManager.COLUMNS; c++){
                //get button
                BeadButton temp = playerBeads.get(r, c);
                temp.setColor(UNSELECTEDPLAYER);
            }
        }
        
        //clear color bead buttons
        for(int r = 0; r <= BoardManager.ROWS; r++) {
            for(int c = 0; c <= BoardManager.COLUMNS; c++){
                //get button
                BeadButton temp = colorBeads.get(r, c);
                temp.setColor(UNSELECTEDCOLOR);
            }
        }
        
        //reset starting beads
        setPlayerBead(BoardManager.ROWS/2 - 1, BoardManager.COLUMNS/2, PlayerColors.WHITE);
        setPlayerBead(BoardManager.ROWS/2, BoardManager.COLUMNS/2, PlayerColors.BLACK);
        
        //start game
        setPlayerBeadsActive();
    }
    
    private void endGame() throws IOException {
        //score game
        ScoreController temp = (ScoreController)this.getSceneManager().getControllerAt("Score");
        temp.scoreGame();
        
        //clear autosave
        clearSave();
        
        //switch to score screen
        this.getSceneManager().switchTo("Score");
    }
    
    //activate all remaining color beads
    private void setPhaseTwoActive() throws IOException {
        if(boardManager.getBeadsPlayed() == (BoardManager.COLUMNS + 1) * (BoardManager.ROWS + 1)) {
            //end game
            endGame();
        } else {
            //cycle color beads
            for(int r = 0; r < BoardManager.ROWS + 1; r++) {
                for(int c = 0; c < BoardManager.COLUMNS+1; c++) {
                    //check if bead should be active based on previously selected bead
                    if(boardManager.checkIfColorBeadActive(r, c)) {
                        //if active, set glow and active
                        colorBeads.get(r, c).setBorder(BORDERBLUE);
                        colorBeads.get(r, c).setDisable(false);
                    } else {
                        //if inactive, clear glow and disable
                        colorBeads.get(r, c).setBorder(BORDERBLACK);
                        colorBeads.get(r, c).setDisable(true);
                    }
                }
            }
            //cycle color beads
            for(int r = 0; r < BoardManager.ROWS; r++) {
                for(int c = 0; c < BoardManager.COLUMNS; c++) {
                    playerBeads.get(r, c).setDisable(true);
                    playerBeads.get(r, c).setBorder(BORDERBLACK);
                }
            }
        }
    }
    
    //activate buttons for player bead placement
    private void setPlayerBeadsActive() throws IOException {
        //auto save
        autoSave();
        
        //change color select buttons
        Player currentPlayer = boardManager.getCurrentPlayer();
        BeadColors selectedColor = currentPlayer.getSelectedColor();
        setSelectedColorSelectButton(selectedColor);
        
        //change active player labels
        PlayerColors currentPlayerColor = currentPlayer.getPlayerColor();
        switch(currentPlayerColor) {
            case WHITE:
                whiteCurrent.setText("Current:");
                blackCurrent.setText("");
                break;
            case BLACK:
                whiteCurrent.setText("");
                blackCurrent.setText("Current:");
                break;
        }
        
        //go to next phase if no player beads remain
        if(boardManager.getCurrentPlayer().getRemainingBeads() == 0) {
            boardManager.changeFinalPhase();
            this.setPhaseTwoActive();
        } else {
            //cycle player beads
            for(int r = 0; r < BoardManager.ROWS; r++) {
                for(int c = 0; c < BoardManager.COLUMNS; c++) {
                    //check if bead should be active
                    if(boardManager.checkIfPlayerBeadActive(r, c)) {
                        //if active, set glow and active
                        playerBeads.get(r, c).setBorder(BORDERBLUE);
                        playerBeads.get(r, c).setDisable(false);
                    } else {
                        //if inactive, clear glow and disable
                        playerBeads.get(r, c).setBorder(BORDERBLACK);
                        playerBeads.get(r, c).setDisable(true);
                    }
                }
            }
            //cycle color beads
            for(int r = 0; r < BoardManager.ROWS+1; r++) {
                for(int c = 0; c < BoardManager.COLUMNS+1; c++) {
                    //disable all color beads
                    colorBeads.get(r, c).setDisable(true);
                    colorBeads.get(r, c).setBorder(BORDERBLACK);
                }
            }
        }
    }
    
    //activate color beads next to selected player bead
    private void setColorBeadsActive(int row, int col) throws IOException {
        //flag to track if any color beads can be placed
        boolean flag = false;
        
        //cycle color beads
        for(int r = 0; r < BoardManager.ROWS+1; r++) {
            for(int c = 0; c < BoardManager.COLUMNS+1; c++) {
                //check if bead should be active based on previously selected bead
                if(boardManager.checkIfColorBeadActive(row, col, r, c)) {
                    //if active, set glow and active
                    colorBeads.get(r, c).setBorder(BORDERBLUE);
                    colorBeads.get(r, c).setDisable(false);
                    flag = true;
                } else {
                    //if inactive, clear glow and disable
                    colorBeads.get(r, c).setBorder(BORDERBLACK);
                    colorBeads.get(r, c).setDisable(true);
                }
            }
        }
        //cycle color beads
        for(int r = 0; r < BoardManager.ROWS; r++) {
            for(int c = 0; c < BoardManager.COLUMNS; c++) {
                //disable all player beads
                playerBeads.get(r, c).setDisable(true);
                playerBeads.get(r, c).setBorder(BORDERBLACK);
            }
        }
        
        //if no color beads active, pass turn
        if(!flag) {
            boardManager.changePhase();
            boardManager.changeTurns();
            setPlayerBeadsActive();
        }
    }
    
    public void load() throws IOException, ClassNotFoundException {
        boardManager.load();
        this.reloadVisuals();
        this.setPlayerBeadsActive();
    }
    
    private void autoSave() throws FileNotFoundException, IOException {
        boardManager.save();
    }
    
    public void clearSave() throws IOException {
        boardManager.clearSave();
    }
}
