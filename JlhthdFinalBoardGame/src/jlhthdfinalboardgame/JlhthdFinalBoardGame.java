/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jlhthdfinalboardgame;

import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_MODENA;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jlhthdfinalboardgame.scenemanagement.SceneManager;

/**
 *
 * @author jlhth_000
 */
public class JlhthdFinalBoardGame extends Application {
    private SceneManager sceneManager;
    
    @Override
    public void start(Stage stage) throws Exception {
        // UI if SceneManager can't switch to a Scene
        HBox root = new HBox();
        root.setPrefSize(600, 400);
        root.setAlignment(Pos.CENTER);
        Text message = new Text("Loading");
        message.setFont(Font.font(STYLESHEET_MODENA, 32));
        root.getChildren().add(message);
        
        // create Scene and init UI to show failure in case switch fails
        Scene scene = new Scene(root);
        
        sceneManager = SceneManager.getInstance(scene);
        sceneManager.switchTo("MainMenu");
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
