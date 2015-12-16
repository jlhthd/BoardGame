/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.scenemanagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jlhthdfinalboardgame.boardmanagement.BoardManager;

/**
 * FXML Controller class
 *
 * @author jlhth_000
 */
public class ScoreController extends LoadableScene implements Initializable {
    
    @FXML
    private Label winner;
    
    @FXML
    private Label blackScore;
    
    @FXML
    private Label whiteScore;
    
    private final BoardManager boardManager = BoardManager.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void scoreGame() {
        boardManager.scoreGame();
        Integer whiteScoreVal = boardManager.getWhiteScore();
        Integer blackScoreVal = boardManager.getBlackScore();
        
        whiteScore.setText(whiteScoreVal.toString());
        blackScore.setText(blackScoreVal.toString());
        
        if(whiteScoreVal > blackScoreVal) {
            winner.setText("White Wins!");
        } else if(blackScoreVal > whiteScoreVal) {
            winner.setText("Black Wins!");
        } else {
            winner.setText("It's a Tie!");
        }
    }
}
