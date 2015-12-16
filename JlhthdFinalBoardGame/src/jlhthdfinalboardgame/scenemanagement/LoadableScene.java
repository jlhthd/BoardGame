/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame.scenemanagement;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author jlhth_000
 */
public abstract class LoadableScene {
    private Parent root;
    private SceneManager sceneManager;
    
    //alert constants
    private static final double ALERTWIDTH = 700.0;
    private static final double ALERTHEIGHT = 800.0;
    //this should probably be loaded from a file
    private static final String HELPTEXT = "-The objective of the game is to score the most points.\n"
            + "-Points are scored based on the number of different colors surrounding each of your player pieces.\n"
            + "-On each turn, the current player first places one of their player markers on the board claiming the square.\n"
            + "-A square can only be claimed if it is adjacent, horizontally or vertically but not diagonally, to a piece that has already been placed.\n"
            + "-The player then places a colored piece on one of the unclaimed corners of the square that they just claimed.\n"
            + "-Once all squares have been claimed by a player, players will then take turns placing colored beads into any unclaimed corners that remain.\n"
            + "-Once all corners have been claimed, the game is over and players are scored.";
    private static final String ABOUTTEXT = "Created by: Joshua Heffron\n"
            + "Version: 1.0";
    
    public void setRoot(Parent root) {
        this.root = root;
    }
    
    public Parent getRoot() {
        return this.root;
    }
    
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    
    public SceneManager getSceneManager() {
        return this.sceneManager;
    }
    
    @FXML
    private void handleNew(ActionEvent event) throws IOException {
        //clear board and save
        BoardController temp = (BoardController) this.getSceneManager().getControllerAt("Board");
        temp.clearSave();
        temp.clear();
        
        //switch to board
        this.getSceneManager().switchTo("Board");
    }
    
    @FXML
    private void handleResume(ActionEvent event) throws IOException, ClassNotFoundException {
        //load previous data into board
        BoardController temp = (BoardController) this.getSceneManager().getControllerAt("Board");
        temp.load();
        
        //switch to board
        this.getSceneManager().switchTo("Board");
    }
    
    @FXML
    private void handleClose(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    private void handleHelp(ActionEvent event) {
        //create help alert
        Alert help = new Alert(AlertType.INFORMATION);
        help.setTitle("Help");
        help.setWidth(ALERTWIDTH);
        help.setHeight(ALERTHEIGHT);
        help.setHeaderText("How to play");
        help.setContentText(HELPTEXT);
        
        //show help alert
        help.showAndWait();
    }
    
    @FXML
    private void handleAbout(ActionEvent event) {
        //create about alert
        Alert about = new Alert(AlertType.INFORMATION);
        about.setTitle("About");
        about.setWidth(ALERTWIDTH);
        about.setHeight(ALERTHEIGHT);
        about.setHeaderText("About");
        about.setContentText(ABOUTTEXT);
        
        //show about alert
        about.showAndWait();
    }
    
    @FXML
    private void handleMainMenu(ActionEvent event) {
        //set resume button activity
        MainMenuController temp = (MainMenuController) this.getSceneManager().getControllerAt("MainMenu");
        temp.setResumeButtonActive();
        
        //switch to main menu
        this.getSceneManager().switchTo("MainMenu");
    }
}
