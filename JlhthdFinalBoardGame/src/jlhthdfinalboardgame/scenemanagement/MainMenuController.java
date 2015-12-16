/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.scenemanagement;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import jlhthdfinalboardgame.boardmanagement.BoardManager;

/**
 * FXML Controller class
 *
 * @author jlhth_000
 */
public class MainMenuController extends LoadableScene implements Initializable {
    
    @FXML
    private Button resumeButton;
    
    private final BoardManager boardManager = BoardManager.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setResumeButtonActive();
    }    
    
    public void setResumeButtonActive() {
        try {
            if(boardManager.checkForSave()) {
                resumeButton.setDisable(false);
            } else {
                resumeButton.setDisable(true);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
